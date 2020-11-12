package com.yusun.rpc.framework;

import java.util.Arrays;

/**
 * @author yu 2020/11/8.
 */
public class RpcRequest {

    private String id;
    /**
     * rpc接口
     */
    private String interfaceName;

    /**
     * rpc接口方法名
     */
    private String methodName;

    /**
     * 接口的参数类型
     */
    private Class[] paramTypes;

    /**
     * 接口的参数
     */
    private Object[] params;

    public static RpcRequest builder(){
        return new RpcRequest();
    }

    public String getId() {
        return id;
    }

    public RpcRequest setId(String id) {
        this.id = id;
        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public RpcRequest setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RpcRequest setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public RpcRequest setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public RpcRequest setParams(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"interfaceName\":\"")
                .append(interfaceName).append('\"');
        sb.append(",\"methodName\":\"")
                .append(methodName).append('\"');
        sb.append(",\"paramTypes\":")
                .append(Arrays.toString(paramTypes));
        sb.append(",\"params\":")
                .append(Arrays.toString(params));
        sb.append('}');
        return sb.toString();
    }
}
