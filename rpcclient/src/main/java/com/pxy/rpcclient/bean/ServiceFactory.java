package com.pxy.rpcclient.bean;

import com.pxy.rpcclient.proxy.ServiceProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ServiceFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceType;//interfaceType: com.pxy.rpcclient.service.UserService

    public ServiceFactory(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    //根据接口类型创建一个代理类实例
    @Override
    public T getObject() {
        InvocationHandler handler = new ServiceProxy<>(interfaceType);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class[]{interfaceType}, handler);
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}