package com.yusun.rpc.provider.impl;

import com.power.common.model.CommonResult;
import com.yusun.rpc.provider.api.HelloService;

/**
 * @author yu 2020/11/8.
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello: "+name;
    }

    @Override
    public CommonResult<String> sayHello2(String name) {
        return CommonResult.ok().setResult("Hello: "+name);
    }
}
