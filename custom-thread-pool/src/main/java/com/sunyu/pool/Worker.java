package com.sunyu.pool;

import com.sunyu.pool.factory.CustomThreadFactory;

import java.util.Queue;
import java.util.concurrent.ThreadFactory;

/**
 * @author yu 2020/11/14.
 */
public class Worker implements Runnable {
    private Runnable task;
    protected Thread thread;
    private CustomQueue queue;
    private final CustomThreadPool pool;

    /**
     * 构造方法，初始化worker
     *
     * @param task
     */
    Worker(Runnable task, CustomQueue queue, CustomThreadFactory factory, CustomThreadPool pool) {
        this.task = task;
        this.thread = factory.createThread(this);
        this.queue = queue;
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            while (task != null || (task = getTask()) != null) {
                task.run();
                //执行完了则把task置为空，让它执行getTask去队列里面去取
                task = null;
            }
        } finally {
            //如果已经没有任务可以执行了，则回收掉这个线程
            pool.processWorkerExit(this);
        }
    }

    /**
     * 从队列中获取一个任务
     *
     * @return
     */
    private Runnable getTask() {
        return queue.poll();
    }

}

