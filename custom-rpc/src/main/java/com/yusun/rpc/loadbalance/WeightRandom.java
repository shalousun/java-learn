package com.yusun.rpc.loadbalance;

import java.util.Random;

/**
 * 权重
 * @author yu 2021/1/13.
 */
public class WeightRandom {

    public static String getServer() {
        int totalWeight = 0;
        for (Integer weight : Servers.SERVER_LIST.values()) {
            totalWeight += weight;
        }
        Random random = new Random();
        int pos = random.nextInt(totalWeight);
        for (String ip : Servers.SERVER_LIST.keySet()) {
            int weight = Servers.SERVER_LIST.get(ip);
            if (pos < weight) {
                return ip;
            }
            pos = pos - weight;
        }
        return "";
    }
}
