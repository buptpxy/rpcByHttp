package com.pxy.rpcserver.rpc;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {
    @RequestMapping("/")
    public Result rpcMain(RpcParam rpcParam) {
        return getResult(rpcParam);
    }

    public Result getResult(RpcParam rpcParam) {
        try {
            Class clazz = ClassUtil.getClazz(rpcParam.getIdentifier());
            Class[] argClassArray = ClassUtil.getArgTypes(rpcParam.getArgTypes());
            Object[] args = ClassUtil.getArgObjects(rpcParam.getArgValues(),argClassArray);
            Method method = clazz.getMethod(rpcParam.getMethodName(), argClassArray);
            Object obj = ServiceGetter.getServiceByClazz(clazz);
            Object result = method.invoke(obj, args);
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