package com.sunyu.transaction.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyu.transaction.enums.TransactionType;
import com.sunyu.transaction.transaction.MyTransaction;
import com.sunyu.transaction.transaction.MyTransactionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;



/**
 * @author yu 2021/1/2.
 */
public class NettyClientHandler  extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;//上下文
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已经连上服务端");
        this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收数据："+msg.toString());
        JSONObject jsonObject = JSON.parseObject((String)msg);
        String groupId = jsonObject.getString("groupId");
        String command = jsonObject.getString("command");
        System.out.println("接收的command:"+command);
        // 根据 groupId获取本地的分支事务
        MyTransaction myTransaction = MyTransactionManager.getTransaction(groupId);
        if("rollback".equals(command)){
            myTransaction.setTransactionType(TransactionType.rollback);
        } else {
            myTransaction.setTransactionType(TransactionType.commit);
        }
        //唤醒
        super.channelRead(ctx, msg);
    }

    public Object call(JSONObject jsonObject) throws Exception {
        return context.writeAndFlush(jsonObject.toJSONString());
    }
}
