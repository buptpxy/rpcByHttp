package com.pxy.rpcclient.utils;

import com.alibaba.fastjson.JSON;
import com.pxy.rpcclient.proxy.RemoteClass;
import com.pxy.rpcclient.rpc.RpcParam;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RpcUtil {
    public static RpcParam getRpcParam(Method method, Object[] args, RemoteClass remoteClass) {
        List<String> argTypeList = new ArrayList<>();
        //8中基本类型也可得到
        Class[] types = method.getParameterTypes();
        if (types!=null) {
            for (Class typeClazz:types) {
                argTypeList.add(typeClazz.getName());
            }
        }
        String argTypes = JSON.toJSONString(argTypeList);
        String argValues = JSON.toJSONString(args);
        return new RpcParam(remoteClass.value(), method.getName(), argTypes, argValues);
    }
}
