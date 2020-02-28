package com.pxy.rpcclient.rpc;

public class RpcParam {
    private String identifier;
    private String methodName;
    private String argTypes;
    private String argValues;

    public RpcParam(String identifier,String methodName,String argTypes,String argValues) {
        this.identifier = identifier;
        this.methodName = methodName;
        this.argTypes = argTypes;
        this.argValues = argValues;
    }
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
