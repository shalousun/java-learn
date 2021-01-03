package com.sunyu.transaction.transaction;

import com.alibaba.fastjson.JSONObject;
import com.sunyu.transaction.annotation.MyTransactional;
import com.sunyu.transaction.enums.TransactionType;
import com.sunyu.transaction.netty.NettyClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yu 2021/1/2.
 */
public class MyTransactionManager {

    private static NettyClient nettyClient;

    private static ThreadLocal<MyTransactional> current = new ThreadLocal<>();

    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();

    private static ThreadLocal<Integer> transactionCount = new ThreadLocal<>();

    public void setNettyClient(NettyClient nettyClient) {
        MyTransactionManager.nettyClient = nettyClient;
    }

    public static Map<String, MyTransaction> TRANSACTION_MAP = new HashMap<>();

    public static String createTransactionGroup() {
        String groupId = UUID.randomUUID().toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", groupId);
        jsonObject.put("command", "create");
        nettyClient.send(jsonObject);
        System.out.println("创建事务组");
        currentGroupId.set(groupId);
        return groupId;
    }

    /**
     * 创建子事务
     *
     * @param groupId
     * @return
     */
    public static MyTransactional createTransaction(String groupId) {
        String transactionId = UUID.randomUUID().toString();

        return null;
    }

    public static void addTransaction(String groupId, TransactionType transactionType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", groupId);
        jsonObject.put("transactionType", transactionType);
        jsonObject.put("command", "add");
        String transactionId = UUID.randomUUID().toString();
        MyTransaction myTransaction = new MyTransaction(groupId,transactionId,transactionType);
        TRANSACTION_MAP.put(groupId,myTransaction);
        nettyClient.send(jsonObject);
        System.out.println("添加事务");
    }

    public static MyTransaction getTransaction(String groupId){
        return TRANSACTION_MAP.get(groupId);
    }
}
