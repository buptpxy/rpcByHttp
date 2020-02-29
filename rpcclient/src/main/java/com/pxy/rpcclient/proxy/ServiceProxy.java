package com.pxy.rpcclient.proxy;

import com.alibaba.fastjson.JSON;
import com.pxy.rpcclient.utils.ClassUtil;
import com.pxy.rpcclient.utils.HttpUtil;
import com.pxy.rpcclient.rpc.Result;
import com.pxy.rpcclient.rpc.RpcParam;
import com.pxy.rpcclient.utils.RpcUtil;

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
        RemoteClass remoteClass = ClassUtil.getRemoteClass(method);
        RpcParam rpcParam = RpcUtil.getRpcParam(method, args, remoteClass);
        return getProxyObject(rpcParam);
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

}