package com.pxy.rpcserver.rpc;

import java.io.Serializable;

public class RpcParam implements Serializable {
    private String className;
    private String methodName;
    private String argTypes;
    private String argValues;

    public RpcParam(String className, String methodName, String argTypes, String argValues) {
        this.className = className;
        this.methodName = methodName;
        this.argTypes = argTypes;
        this.argValues = argValues;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(String argTypes) {
        this.argTypes = argTypes;
    }

    public String getArgValues() {
        return argValues;
    }

    public void setArgValues(String argValues) {
        this.argValues = argValues;
    }


}
