package com.yusun.rpc.protocal.dubbo;

import com.yusun.rpc.protocal.dubbo.codec.JSONDecoder;
import com.yusun.rpc.protocal.dubbo.codec.JSONEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * @author yu 2020/11/8.
 */
public class NettyServer {

    public void start(String hostName,Integer port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap =
                    new ServerBootstrap()
                            .group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class).localAddress(port)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
                                    pipeline.addLast(new IdleStateHandler(0, 0, 60));
                                    pipeline.addLast(new JSONEncoder());
                                    pipeline.addLast(new JSONDecoder());
                                    pipeline.addLast(new NettyServerHandler());
                                }
                            })
                            .option(ChannelOption.SO_BACKLOG, 128)
                            .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(hostName,port).sync();
            System.out.println("RPC Server start listen at " + port);
           // 等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
