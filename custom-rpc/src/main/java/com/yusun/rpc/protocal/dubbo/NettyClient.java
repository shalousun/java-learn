package com.yusun.rpc.protocal.dubbo;

import com.yusun.rpc.framework.RpcRequest;
import com.yusun.rpc.framework.URL;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yu 2020/11/8.
 */
public class NettyClient {

    private static ExecutorService executorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private NettyClientHandler nettyClientHandler;

    public NettyClient(URL url){
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
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(nettyClientHandler);
                    }
                });
        try {
            bootstrap.connect(url.getHostname(),url.getPort()).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T send(RpcRequest rpcRequest) {
        try {
            this.nettyClientHandler.setParam(rpcRequest);
            return (T) executorService.submit(nettyClientHandler).get();
        }catch (Exception e){
            return null;
        }
    }
}
