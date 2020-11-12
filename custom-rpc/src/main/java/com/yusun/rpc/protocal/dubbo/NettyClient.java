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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author yu 2020/11/8.
 */
public class NettyClient {

    private EventLoopGroup group = new NioEventLoopGroup(1);
    private Bootstrap bootstrap = new Bootstrap();

    private NettyClientHandler nettyClientHandler;

    public NettyClient(URL url) {
        nettyClientHandler = new NettyClientHandler();
        bootstrap.group(group).
                channel(NioSocketChannel.class).
                option(ChannelOption.TCP_NODELAY, true).
                option(ChannelOption.SO_KEEPALIVE, true).
                handler(new ChannelInitializer<SocketChannel>() {
                    //创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        //自定义协议编码器
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        //对象参数类型编码器
                        pipeline.addLast("encoder", new ObjectEncoder());
                        //对象参数类型解码器
                        pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast("handler", nettyClientHandler);
                    }
                });
        try {
            bootstrap.connect(url.getHostname(), url.getPort()).sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    public String send(RpcRequest rpcRequest) {
        nettyClientHandler.sendRequest(rpcRequest);
        return (String) nettyClientHandler.getResponse();
    }
}
