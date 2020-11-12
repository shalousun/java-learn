package com.yusun.rpc.provider;

import com.yusun.rpc.framework.Protocol;
import com.yusun.rpc.framework.ProtocolFactory;
import com.yusun.rpc.framework.URL;

import com.yusun.rpc.protocal.http.HttpServer;
import com.yusun.rpc.provider.api.HelloService;
import com.yusun.rpc.provider.impl.HelloServiceImpl;
import com.yusun.rpc.register.LocalRegister;
import com.yusun.rpc.register.RemoteMapRegister;

/**
 * @author yu 2020/11/8.
 */
public class Provider {

    public static void main(String[] args) {
        System.setProperty("protocolName", "dubbo");
        protocol();
    }

    /**
     * 使用协议实现
     */
    public static void protocol() {
        URL url = URL.builder().setHostname("localhost").setPort(8080);
        //利用文件代替注册中心
        RemoteMapRegister.registry(HelloService.class.getName(), url);
        //注册服务
        LocalRegister.registry(HelloService.class.getName(), HelloServiceImpl.class);
        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);
    }


    public static void simple() {
        URL url = URL.builder().setHostname("localhost").setPort(8080);
        RemoteMapRegister.registry(HelloService.class.getName(), url);
        LocalRegister.registry(HelloService.class.getName(), HelloServiceImpl.class);
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
