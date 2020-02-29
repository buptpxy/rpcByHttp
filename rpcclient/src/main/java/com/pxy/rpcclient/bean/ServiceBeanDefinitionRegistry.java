package com.pxy.rpcclient.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 为接口注入实现类
 * 通过实现BeanDefinitionRegistryPostProcessor是beanDefinition的后处理器，在创建bean的过程中使用，获取Bean，从而获得自定义注解。
 *
 * ResourceLoader用于加载资源
 *
 * 为了让Bean获取它所在的Spring容器，可以让该Bean实现ApplicationContextAware接口。
 */
@Component
public class ServiceBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware, ApplicationContextAware {
    public static final String BASE_PACKAGE = "com.pxy.rpcclient.service";
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private static ApplicationContext applicationContext;
    private MetadataReaderFactory metadataReaderFactory;
    private ResourcePatternResolver resourcePatternResolver;


    //扫描定义的远程类的接口，并将它们注册为bean
    //这里一般我们是通过反射获取需要代理的接口的clazz列表
    //比如扫描包下面的类，或者通过某注解标注的类等等
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<Class<?>> clazzSet = scannerPackages(BASE_PACKAGE);
        clazzSet.stream().filter(Class::isInterface).forEach(x -> registerBean(registry, x));
    }

    //将扫描到的bean注册到spring容器中
    //在这里，我们可以给该对象的属性注入对应的实例。
    //比如mybatis，就在这里注入了dataSource和sqlSessionFactory，
    // 注意，如果采用definition.getPropertyValues()方式的话，
    // 类似definition.getPropertyValues().add("interfaceType", beanClazz);
    // 则要求在FactoryBean（本应用中即ServiceFactory）提供setter方法，否则会注入失败
    // 如果采用definition.getConstructorArgumentValues()，
    // 则FactoryBean中需要提供包含该属性的构造方法，否则会注入失败
    private void registerBean(BeanDefinitionRegistry registry, Class clazz) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();//得到bean的定义
        definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);//注入构造函数参数
        definition.setBeanClass(ServiceFactory.class);//设置工厂类，即指定bean的实例化方式
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);//设置bean的注入方式,这里采用的是byType方式注入，类似的还有byName等
        registry.registerBeanDefinition(clazz.getSimpleName(), definition);//注册bean
    }

    /**
     * 获取指定路径及子路径下的所有类
     */
    private Set<Class<?>> scannerPackages(String basePackage) {
        Set<Class<?>> set = new LinkedHashSet<>();
        Resource[] resources = getResources(basePackage);
        for (Resource resource : resources) {
            try {
                set.add(getClass(resource));//得到资源的类对象
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ResourceAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    private Resource[] getResources(String basePackage) {
        String className = applicationContext.getEnvironment().resolveRequiredPlaceholders(basePackage);
        String basePackageName = ClassUtils.convertClassNameToResourcePath(className);
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                basePackageName + '/' + DEFAULT_RESOURCE_PATTERN;
        Resource[] resources = new Resource[0];
        try {
            resources = this.resourcePatternResolver.getResources(packageSearchPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private Class<?> getClass(Resource resource) throws IOException, ResourceAccessException, ClassNotFoundException {
        if (!resource.isReadable()) {
            throw new ResourceAccessException("resource is not exist!");
        }
        MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
        String className = metadataReader.getClassMetadata().getClassName();
        Class<?> clazz = Class.forName(className);;
        return clazz;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}