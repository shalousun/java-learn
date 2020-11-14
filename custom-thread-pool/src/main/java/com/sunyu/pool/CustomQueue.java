package com.sunyu.pool;

/**
 * @author yu 2020/11/14.
 */
public interface CustomQueue {
    boolean offer(Runnable run);

    Runnable poll();

    int size();
}
