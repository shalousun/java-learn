package com.yusun.rpc.loadbalance;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yu 2021/1/13.
 */
public class Servers {

    public final static Map<String,Integer> SERVER_LIST = new HashMap<>();
    static {
        SERVER_LIST.put("172.31.1.101",5);
        SERVER_LIST.put("172.31.1.102",5);
        SERVER_LIST.put("172.31.1.103",5);
    }
}
