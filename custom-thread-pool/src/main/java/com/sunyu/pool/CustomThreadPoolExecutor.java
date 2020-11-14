package com.sunyu.pool;

import com.sunyu.pool.factory.CustomThreadFactory;
import com.sunyu.pool.handler.RejectedHandler;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yu 2020/11/14.
 */
public class CustomThreadPoolExecutor extends CustomAbstractThreadPool {

    //拒绝策略
    private final RejectedHandler handler;

    //记录工作线程的数量
    private AtomicInteger workCount;

    //定义一把锁
    private final ReentrantLock mainLock = new ReentrantLock();

    //任务队列
    private final CustomQueue queue;

    //核心线程数
    private final int corePoolSize;

    //最大线程数
    private final int maximumPoolSize;

    //创建线程的工厂
    private CustomThreadFactory factory;

    //存放线程
    private final HashSet<Worker> workers = new HashSet();


    /**
     * 构造方法，初始化线程池参数
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param queue 任务队列
     * @param handler 拒绝策略
     * @param factory 线程工厂--可以给线程起名
     */
    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, CustomQueue queue,
                             RejectedHandler handler, CustomThreadFactory factory) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.queue = queue;
        this.workCount = new AtomicInteger();
        this.handler = handler;
        this.factory = factory;
    }

    /**
     * 执行提交的任务
     *
     * @param task
     */
    @Override
    public void execute(Runnable task) {
        if (task == null) throw new NullPointerException();

        /**
         * 当前工作线程数
         * 1、wc < corePoolSize,新增一个核心线程worker
         * 2、wc > corePoolSize,把task入队
         *    1）入队成功->判断线程池是否还有工作线程，没有的话再创建一个工作线程
         *    2）入队失败（队列已满）->创建空闲工作线程
         *       ①创建成功->执行task
         *       ②创建失败（空闲线程已满）->执行拒绝策略
         */
        int wc = workCount.get();
        if (wc < corePoolSize) {
            if (addWorker(task, true)) {
                return;
            }

        }
        if (queue.offer(task)) {
            //假如创建的线程池的核心线程数为空
            //那么一开始就没有工作线程，这种情况创建空闲线程
            //给个null的任务给它则会从队列中取任务
            wc = workCount.get();
            if (wc == 0) {
                addWorker(null, false);
            }

        } else if (!addWorker(task, false)) {//队列满了，我们尝试创建空闲线程
            //如果创建线程失败，则执行拒绝策略
            handler.rejected(task, this);
        }
    }


    /**
     * @param task 执行的任务
     * @param core 核心线程数 or 最大线程数
     * @return
     */
    private boolean addWorker(Runnable task, boolean core) {
        //cas 把工作线程数先加上
        for (; ; ) {
            int wc = workCount.get();
            int size = core ? corePoolSize : maximumPoolSize;
            if (wc >= size)//工作线程已经达到了最大则返回false
                return false;
            if (workCount.compareAndSet(wc, wc + 1)) {
                break;
            }
        }

        //创建一个工作线程
        Worker w = new Worker(task, queue, factory, this);
        Thread t = w.thread;

        boolean workerStarted = false;
        boolean workerAdded = false;

        if (t != null) {
            //判断线程的状态
            if (t.isAlive()) {
                throw new IllegalThreadStateException();
            }


            /**
             * 先存储新new的worker
             */
            mainLock.lock();
            try {
                workers.add(w);
                workerAdded = true;
            } finally {
                mainLock.unlock();
            }
        }

        try {
            /**
             * 存储成功后再启动线程
             */
            if (workerAdded) {
                t.start();//启动线程
                workerStarted = true;
                if (!core) {
                    System.out.println("队列中有" + queue.size() + "个任务，已满创建空闲线程");
                }
            }
        } finally {
            if (!workerStarted) {
                //释放work
                processWorkerExit(w);
            }
        }

        return workerStarted;
    }

    /**
     * 释放工作线程
     *
     * @param worker
     */
    @Override
    public void processWorkerExit(Worker worker) {
        mainLock.lock();
        try {
            //释放掉工作线程
            workers.remove(worker);
            workCount.decrementAndGet();
        } finally {
            mainLock.unlock();
        }
    }
}
