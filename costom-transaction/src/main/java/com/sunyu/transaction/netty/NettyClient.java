package com.sunyu.transaction.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author yu 2021/1/2.
 */
public class NettyClient {
    private NettyClientHandler nettyClientHandler;
    public NettyClient(String hostName, int port){
        nettyClientHandler = new NettyClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder",new StringDecoder());
                        pipeline.addLast("encoder",new StringEncoder());
                        pipeline.addLast(nettyClientHandler);
                    }
                });
        try {
            bootstrap.connect(hostName,port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(JSONObject jsonObject){
        try {
            //client.call(jsonObject);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
