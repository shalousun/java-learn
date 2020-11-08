package com.yusun.rpc.protocal.http;


import com.yusun.rpc.consumer.HttpClient;
import com.yusun.rpc.framework.Invocation;
import com.yusun.rpc.framework.Protocol;
import com.yusun.rpc.framework.URL;

/**
 * @author yu 2020/11/8.
 */
public class HttpProtocol implements Protocol {

    @Override
    public void start(URL url) {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(),url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        HttpClient httpClient = new HttpClient();
        return httpClient.send(url,invocation);
    }
}
