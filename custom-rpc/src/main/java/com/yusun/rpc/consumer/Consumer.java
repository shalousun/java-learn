package com.yusun.rpc.consumer;

import com.yusun.rpc.framework.Invocation;
import com.yusun.rpc.framework.ProxyFactory;
import com.yusun.rpc.provider.api.HelloService;

/**
 * @author yu 2020/11/8.
 */
public class Consumer {

    public static void main(String[] args) {
        useProxy();
    }

    /**
     * 使用代理
     */
    private static void useProxy(){
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("yusun7");
        System.out.println(result);
    }

    /**
     * 不是用代理
     */
    private static void noProxy(){
        HttpClient client = new HttpClient();
        Invocation invocation = Invocation.builder().setInterfaceName(HelloService.class.getName())
                .setMethodName("sayHello").setParamTypes(new Class[]{String.class})
                .setParams(new Object[]{"sunyu"});
        String result = client.send("localhost",8080,invocation);
        System.out.println(result);
    }
}
