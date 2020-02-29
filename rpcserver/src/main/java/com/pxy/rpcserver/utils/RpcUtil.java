package com.pxy.rpcserver.utils;

import com.alibaba.fastjson.JSON;
import com.pxy.rpcserver.rpc.Result;
import com.pxy.rpcserver.rpc.RpcParam;
import com.pxy.rpcserver.rpc.ServiceGetException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class RpcUtil {
    public static Result getServerResult(RpcParam rpcParam) {
        try {
            Class clazz = Class.forName(rpcParam.getClassName());
            Object ServiceObj = ServiceGetterUtil.getServiceByClazz(clazz);

            Class[] argTypes = ClassUtil.getArgTypes(rpcParam.getArgTypes());
            Method method = clazz.getMethod(rpcParam.getMethodName(), argTypes);
            Object[] args = ClassUtil.getArgObjects(rpcParam.getArgValues(),argTypes);

            Object result = method.invoke(ServiceObj, args);
            return Result.getSuccessResult(method.getReturnType().getName(), JSON.toJSONString(result));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return Result.getFailResult("目标类不存在");
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            return Result.getFailResult("目标方法不存在");
        } catch (ServiceGetException e) {
            e.printStackTrace();
            return Result.getFailResult("目标类的实例无法生成");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Result.getFailResult("目标类的方法无调用权限");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return Result.getFailResult("目标类的方法调用失败");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.getFailResult("服务端解析异常");
        }
    }
}
