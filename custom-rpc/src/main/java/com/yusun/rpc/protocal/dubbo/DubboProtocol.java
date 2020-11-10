package com.yusun.rpc.protocal.dubbo;

import com.yusun.rpc.framework.RpcRequest;
import com.yusun.rpc.framework.Protocol;
import com.yusun.rpc.framework.URL;

/**
 * @author yu 2020/11/8.
 */
public class DubboProtocol implements Protocol {

    @Override
    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostname(), url.getPort());
    }

    @Override
    public String send(URL url, RpcRequest rpcRequest) {
        NettyClient nettyClient = new NettyClient();
        return nettyClient.send(url, rpcRequest);
    }
}
