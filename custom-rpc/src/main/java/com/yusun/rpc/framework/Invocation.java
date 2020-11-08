package com.yusun.rpc.framework;

/**
 * @author yu 2020/11/8.
 */
public class Invocation {

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

    public static Invocation builder(){
        return new Invocation();
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public Invocation setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Invocation setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public Invocation setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public Invocation setParams(Object[] params) {
        this.params = params;
        return this;
    }
}
