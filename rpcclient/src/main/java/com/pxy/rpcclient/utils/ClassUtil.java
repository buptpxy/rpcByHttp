package com.pxy.rpcclient.utils;

import com.pxy.rpcclient.proxy.RemoteClass;

import java.lang.reflect.Method;

public class ClassUtil {
    public static Class getArgTypeClass(String typeStr) throws ClassNotFoundException {
        switch (typeStr) {
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "boolean":
                return boolean.class;
            case "char":
                return char.class;
            default:
                return Class.forName(typeStr);
        }
    }
    public static RemoteClass getRemoteClass(Method method) throws Exception {
        RemoteClass remoteClass = method.getDeclaringClass().getAnnotation(RemoteClass.class);
        if (remoteClass == null) {
            throw new Exception("远程类标志未指定");
        }
        return remoteClass;
    }
}
