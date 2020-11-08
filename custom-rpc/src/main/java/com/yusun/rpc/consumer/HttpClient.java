package com.yusun.rpc.consumer;

import com.alibaba.fastjson.JSONObject;
import com.power.common.util.OkHttp3Util;
import com.yusun.rpc.framework.Invocation;
import com.yusun.rpc.framework.URL;

/**
 * @author yu 2020/11/8.
 */
public class HttpClient {

    public String send(String hostName, Integer port, Invocation invocation) {
        String result = OkHttp3Util.syncPostJson("http://" + hostName + ":" + port, JSONObject.toJSONString(invocation));
        return result;
    }

    public String send(URL url, Invocation invocation) {
        String result = OkHttp3Util.syncPostJson("http://" + url.getHostname() + ":" + url.getPort(), JSONObject.toJSONString(invocation));
        return result;
    }
}
