package com.yusun.rpc.protocal.dubbo;

import com.yusun.rpc.protocal.dubbo.codec.JSONDecoder;
import com.yusun.rpc.protocal.dubbo.codec.JSONEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
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
                                    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                    //自定义协议编码器
                                    pipeline.addLast(new LengthFieldPrepender(4));
                                    //对象参数类型编码器
                                    pipeline.addLast("encoder",new ObjectEncoder());
                                    //对象参数类型解码器
                                    pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
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
