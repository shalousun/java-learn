package com.yusun.rpc.provider;

import com.yusun.rpc.entity.URL;
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
        URL url = URL.builder().setHostname("localhost").setPort(8080);
        RemoteMapRegister.registry(HelloService.class.getName(), url);
        LocalRegister.registry(HelloService.class.getName(), HelloServiceImpl.class);
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost",8080);
    }
}
