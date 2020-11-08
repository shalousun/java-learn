package com.yusun.rpc.protocal.dubbo;

import com.yusun.rpc.framework.Invocation;
import com.yusun.rpc.framework.Protocol;
import com.yusun.rpc.framework.URL;

/**
 * @author yu 2020/11/8.
 */
public class DubboProtocol implements Protocol {

    @Override
    public void start(URL url) {

    }

    @Override
    public String send(URL url, Invocation invocation) {
        return null;
    }
}
