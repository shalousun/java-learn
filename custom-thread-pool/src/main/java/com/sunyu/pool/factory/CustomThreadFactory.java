package com.sunyu.pool.factory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yu 2020/11/14.
 */
public class CustomThreadFactory {

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    public Thread createThread(Runnable worker){
        return new Thread(worker,"thread-pool-" + atomicInteger.getAndDecrement());
    }
}
