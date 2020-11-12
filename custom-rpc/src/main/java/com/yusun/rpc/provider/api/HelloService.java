package com.yusun.rpc.provider.api;

import com.power.common.model.CommonResult;

/**
 * @author yu 2020/11/8.
 */
public interface HelloService {

    String sayHello(String name);

    CommonResult<String> sayHello2(String name);
}
