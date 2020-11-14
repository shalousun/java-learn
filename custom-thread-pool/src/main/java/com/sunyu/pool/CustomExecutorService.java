package com.sunyu.pool;

/**
 * @author yu 2020/11/14.
 */
public interface CustomExecutorService {

    void execute(Runnable task);

    void shutdown();

    int getActiveThread();

    Runnable getTask();

}
