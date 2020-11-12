package com.yusun.rpc.framework;

import com.yusun.rpc.protocal.dubbo.DubboProtocol;
import com.yusun.rpc.protocal.http.HttpProtocol;

import java.util.Objects;

/**
 * 工厂模式获取协议
 * @author yu 2020/11/8.
 */
public class ProtocolFactory {

    public static Protocol getProtocol() {
        String protocol = System.getProperty("protocolName");
        System.out.println("Used protocol is:"+protocol);
        if (Objects.isNull(protocol)) {
            protocol = "http";
        }
        switch (protocol) {
            case "dubbo":
                return new DubboProtocol();
            default:
                return new HttpProtocol();
        }
    }
}
