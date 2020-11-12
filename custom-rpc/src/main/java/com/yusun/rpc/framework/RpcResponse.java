package com.yusun.rpc.framework;

/**
 * @author yu 2020/11/13.
 */
public class RpcResponse {
    private String requestId;

    private Object data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
