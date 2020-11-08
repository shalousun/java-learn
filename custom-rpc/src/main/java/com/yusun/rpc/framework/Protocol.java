package com.yusun.rpc.framework;

/**
 * @author yu 2020/11/8.
 */
public interface Protocol {

    /**
     * 启动
     * @param url
     */
    void start(URL url);

    /**
     * 发送远程请求
     * @param url
     * @param invocation 发送数据
     * @return
     */
    String send(URL url,Invocation invocation);
}
