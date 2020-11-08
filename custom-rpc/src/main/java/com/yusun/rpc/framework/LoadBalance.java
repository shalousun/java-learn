package com.yusun.rpc.framework;

import java.util.List;
import java.util.Random;

/**
 * 复杂均衡
 * @author yu 2020/11/8.
 */
public class LoadBalance {

    /**
     * 随机负载
     * @param list
     * @return
     */
    public static URL random(List<URL> list){
        Random random = new Random();
        int n = random.nextInt(list.size());
        return list.get(n);
    }
}
