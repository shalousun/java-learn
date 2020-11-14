package com.yusun.rpc.consumer;

import com.alibaba.fastjson.JSONObject;
import com.power.common.model.CommonResult;
import com.yusun.rpc.framework.RpcProxyFactory;
import com.yusun.rpc.framework.RpcRequest;
import com.yusun.rpc.provider.api.HelloService;

/**
 * @author yu 2020/11/8.
 */
public class Consumer {

    public static void main(String[] args) {
        System.setProperty("protocolName", "dubbo");
        useProxy();
    }

    /**
     * 使用代理
     */
    private static void useProxy() {
        HelloService helloService = RpcProxyFactory.getProxy(HelloService.class);
        CommonResult<String> result = helloService.sayHello2("yusun7");
        System.out.println(JSONObject.toJSONString(result));
    }

    /**
     * 不是用代理
     */
    private static void noProxy() {
        HttpClient client = new HttpClient();
        RpcRequest rpcRequest = RpcRequest.builder().setInterfaceName(HelloService.class.getName())
                .setMethodName("sayHello").setParamTypes(new Class[]{String.class})
                .setParams(new Object[]{"sunyu"});
        String result = client.send("localhost", 8080, rpcRequest);
        System.out.println(result);
    }


}
