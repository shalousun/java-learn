package com.yusun.rpc.framework;

/**
 * @author yu 2020/11/13.
 */
public class RpcResponse {
    /**
     * 响应ID
     */
    private String requestId;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 返回的结果
     */
    private Object result;

    public static RpcResponse builder(){
        return new RpcResponse();
    }

    public String getRequestId() {
        return requestId;
    }

    public RpcResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getError() {
        return error;
    }

    public RpcResponse setError(String error) {
        this.error = error;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RpcResponse setResult(Object result) {
        this.result = result;
        return this;
    }
}
