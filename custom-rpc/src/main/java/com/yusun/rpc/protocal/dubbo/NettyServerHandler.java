package com.yusun.rpc.protocal.dubbo;

import com.alibaba.fastjson.JSONObject;
import com.yusun.rpc.framework.RpcRequest;
import com.yusun.rpc.register.LocalRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yu 2020/11/8.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接成功!" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端断开连接!" + ctx.channel().remoteAddress());
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = JSONObject.parseObject(msg.toString(), RpcRequest.class);
        if ("heartBeat".equals(rpcRequest.getMethodName())) {
            System.out.println("客户端心跳信息..." + ctx.channel().remoteAddress());
        } else {
            String interfaceName = rpcRequest.getInterfaceName();
            System.out.println("RPC客户端请求接口:" + interfaceName + "   方法名:" + rpcRequest.getMethodName());
            try {
                Class clas = LocalRegister.get(interfaceName);
                Method method = clas.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
                String result = (String) method.invoke(clas.newInstance(), rpcRequest.getParams());
                ctx.write(result);
            } catch (NoSuchMethodException | IllegalAccessException
                    | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
