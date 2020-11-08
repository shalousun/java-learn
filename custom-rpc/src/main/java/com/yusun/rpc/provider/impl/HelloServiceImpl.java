package com.yusun.rpc.provider.impl;

import com.yusun.rpc.provider.api.HelloService;

/**
 * @author yu 2020/11/8.
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello: "+name;
    }
}
