package com.pxy.rpcclient.proxy;

import com.alibaba.fastjson.JSON;
import com.pxy.rpcclient.rpc.ClassUtil;
import com.pxy.rpcclient.rpc.HttpUtil;
import com.pxy.rpcclient.rpc.Result;
import com.pxy.rpcclient.rpc.RpcParam;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//使用动态代理是为了给接口造一个假的实现类
public class ServiceProxy<T> implements InvocationHandler {

    // target是要实现的这个接口的类型，例如UserService类型
    private T target;

    public ServiceProxy(T target) {
        this.target = target;
    }

    /**
     * 代理类调用实际类
     * @param proxy 要代理的类对象，例如UserServiceImpl类型的对象
     * @param method 要代理的方法对象，例如UserServiceImpl的getUserInfo方法
     * @param args 要代理的方法的参数对象们
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RemoteClass remoteClass = getRemoteClass(method);
        RpcParam rpcParam = getRpcParam(method, args, remoteClass);
        return getProxyObject(rpcParam);
    }

    private RemoteClass getRemoteClass(Method method) throws Exception {
        RemoteClass remoteClass = method.getDeclaringClass().getAnnotation(RemoteClass.class);
        if (remoteClass == null) {
            throw new Exception("远程类标志未指定");
        }
        return remoteClass;
    }

    private RpcParam getRpcParam(Method method, Object[] args, RemoteClass remoteClass) {
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

    private Object getProxyObject(RpcParam rpcParam) throws Exception {
        Result result = HttpUtil.callRemoteService(rpcParam);
        if (result.isSuccess()) {
            Object proxyObject = JSON.parseObject(result.getResultValue(), ClassUtil.getArgTypeClass(result.getResultType()));
            return proxyObject;
        } else {
            throw new Exception("远程调用异常：" + result.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Object proxyObject = JSON.parseObject("{\"age\":18,\"id\":1,\"name\":\"madongmei\"}", ClassUtil.getArgTypeClass("com.pxy.rpcclient.entity.User"));
            System.out.println(proxyObject.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}