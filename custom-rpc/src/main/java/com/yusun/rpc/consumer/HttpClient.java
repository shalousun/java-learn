package com.yusun.rpc.consumer;

import com.alibaba.fastjson.JSONObject;
import com.power.common.util.OkHttp3Util;
import com.yusun.rpc.framework.RpcRequest;
import com.yusun.rpc.framework.URL;

/**
 * @author yu 2020/11/8.
 */
public class HttpClient {

    public String send(String hostName, Integer port, RpcRequest rpcRequest) {
        String result = OkHttp3Util.syncPostJson("http://" + hostName + ":" + port, JSONObject.toJSONString(rpcRequest));
        return result;
    }

    public String send(URL url, RpcRequest rpcRequest) {
        String result = OkHttp3Util.syncPostJson("http://" + url.getHostname() + ":" + url.getPort(), JSONObject.toJSONString(rpcRequest));
        return result;
    }
}
