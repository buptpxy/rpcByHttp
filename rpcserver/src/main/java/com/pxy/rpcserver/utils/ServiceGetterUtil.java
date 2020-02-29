package com.pxy.rpcserver.utils;

import com.pxy.rpcserver.rpc.ServiceGetException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServiceGetterUtil {
    private static Map<Class, Object> serviceMap = new HashMap<>();
    //根据类对象得到类实例，如果实例化过了就直接从map中取，如果没就实例化后放入map中
    public static <T> T getServiceByClazz(Class<T> clazz) throws ServiceGetException {
        if (serviceMap.containsKey(clazz)) {
            return (T) serviceMap.get(clazz);
        } else {
            //得到类实例
            T bean = null;
            try {
                bean = clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException e) {
                throw new ServiceGetException(e);
            } catch (IllegalAccessException e) {
                throw new ServiceGetException(e);
            } catch (InvocationTargetException e) {
                throw new ServiceGetException(e);
            } catch (NoSuchMethodException e) {
                throw new ServiceGetException(e);
            }
            serviceMap.put(clazz, bean);
            return bean;
        }
    }
}
