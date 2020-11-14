package com.yusun.rpc.protocal.dubbo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yusun.rpc.framework.RpcRequest;
import com.yusun.rpc.framework.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author yu 2020/11/12.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private RpcResponse response;

    private ChannelHandlerContext context;//上下文

    private RpcRequest param;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已经连上服务端");
        this.context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到服务端的数据：" + JSON.toJSONString(msg));
        this.response = (RpcResponse)msg;
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 设置参数
     */
    void setParam(RpcRequest param) {
        this.param = param;
    }
    /**
     * 被代理对象调用，发送数据给服务器 -> wait -> 等待被唤醒(channelRead) -> 返回结果
     */
    @Override
    public synchronized Object call() throws Exception {
        String rpcParam = JSONObject.toJSONString(param);
        System.out.println("客服端发送数据：" + rpcParam);
        context.writeAndFlush(param);
        // 进行 wait,等待channelRead获取到服务器的结果
        wait();
        // 返回结果
        Object object = response.getResult();
        System.out.println(object);
        return object;
    }
}
