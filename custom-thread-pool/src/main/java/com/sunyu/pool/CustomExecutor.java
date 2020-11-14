package com.sunyu.pool;

/**
 * @author yu 2020/11/14.
 */
public interface CustomExecutor {

    /**
     * 执行具体的task
     */
    void execute(Runnable task);
}
