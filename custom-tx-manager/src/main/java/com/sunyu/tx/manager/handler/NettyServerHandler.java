package com.sunyu.tx.manager.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yu 2020/12/29.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private static Map<String, List<String>> transactionTypeMap = new HashMap<>();

    private static Map<String, Boolean> isEndMap = new HashMap<>();

    private static Map<String, Integer> transactionCountMap = new HashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("接收数据为：{}", msg.toString());
        JSONObject jsonObject = JSON.parseObject((String) msg);
        String command = jsonObject.getString("command");//事务组
        String groupId = jsonObject.getString("groupId");

        String transactionType = jsonObject.getString("transactionType");//子事务类型
        Integer transactionCount = jsonObject.getInteger("transactionCount");//事务数量
        Boolean isEnd = jsonObject.getBoolean("isEnd");
        if ("create".equals(command)) {
            transactionTypeMap.put(groupId, new ArrayList<>());
        } else if ("add".equals(command)) {
            transactionTypeMap.get(groupId).add(transactionType);
            if (isEnd) {
                isEndMap.put(groupId, true);
                transactionCountMap.put(groupId, transactionCount);
            }

            JSONObject result = new JSONObject();
            result.put("groupId", groupId);
            // 检查事务是否已经全部到达，如果全部达到检查是否需要回滚
            if (isEndMap.get(groupId) && transactionCountMap.get(groupId)
                    .equals(transactionTypeMap.get(groupId).size())) {
                if (transactionTypeMap.get(groupId).contains("rollback")) {
                    result.put("command", "rollback");
                } else {
                    result.put("command", "commit");
                }
                sendResult(result);
            }
        }


        super.channelRead(ctx, msg);
    }

    private void sendResult(JSONObject jsonObject) {
        List<Channel> channelGroup = new ArrayList<>();
        for (Channel channel : channelGroup) {
            logger.info("发送给客服端的数据：" + jsonObject.toJSONString());
            channel.writeAndFlush(jsonObject);
        }
    }
}
