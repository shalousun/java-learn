package com.yusun.rpc.framework;

import com.power.common.util.UUIDUtil;
import com.yusun.rpc.register.RemoteMapRegister;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author yu 2020/11/8.
 */
public class RpcProxyFactory<T> {

    public static <T> T getProxy(final Class interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                (proxy, method, args) -> {
                    //使用http协议实现
                    Protocol protocol = ProtocolFactory.getProtocol();
                    RpcRequest rpcRequest = RpcRequest.builder().setInterfaceName(interfaceClass.getName())
                            .setMethodName(method.getName()).setParamTypes(method.getParameterTypes())
                            .setParams(args).setId(UUIDUtil.getUuid32());
                    // 从注册中心获取服务地址
                    List<URL> urlList = RemoteMapRegister.get(interfaceClass.getName());
                    // 随机负载获取一台服务器
                    URL url = LoadBalance.random(urlList);
                    System.out.println("server url:" + url.toString());
                    return protocol.send(url, rpcRequest);
                });
    }
}
