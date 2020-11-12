package com.yusun.rpc.protocal.dubbo;

import com.yusun.rpc.framework.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yu 2020/11/12.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private Object response;

    private ChannelHandlerContext context;//上下文

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已经连上服务端");
        this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到服务端的数据："+msg);
        this.response = msg;
    }

    public Object getResponse(){
        return this.response;
    }

    public void sendRequest(RpcRequest request) {
        System.out.println("客服端发送数据："+request.toString());
        this.context.writeAndFlush(request);
    }
}
