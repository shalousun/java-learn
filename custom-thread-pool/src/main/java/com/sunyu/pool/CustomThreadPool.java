package com.sunyu.pool;

/**
 * @author yu 2020/11/14.
 */
public interface CustomThreadPool extends CustomExecutor{

    /**
     * 处理提交的任务
     *
     * @param task
     */
    void submit(Runnable task);

    /**
     * 释放线程
     *
     * @param worker
     */
    void processWorkerExit(Worker worker);
}
