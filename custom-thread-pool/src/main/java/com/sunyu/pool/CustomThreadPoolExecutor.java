package com.sunyu.pool;

import com.sunyu.pool.policy.PolicyHandler;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yu 2020/11/14.
 */
public class CustomThreadPoolExecutor implements CustomExecutorService {

    private AtomicInteger atomicInteger = new AtomicInteger();

    private int poolSize;

    private PolicyHandler policyHandler;
    private final BlockingQueue<Runnable> workQueue;

    private volatile boolean allowThreadTimeOut = false;

    private volatile long keepAliveTime = 0;

    private ReentrantLock mainLock = new ReentrantLock();

    private volatile boolean isShutdown = false;

    private final HashSet<Worker> workers = new HashSet<>();

    private volatile long completeTaskCount;


    public CustomThreadPoolExecutor(int poolSize, int queueSize, long keepAliveTime, PolicyHandler policyHandler) {
        if (poolSize <= 0) {
            throw new IllegalArgumentException("核心线程数不能为空");
        }
        this.poolSize = poolSize;
        this.policyHandler = policyHandler;
        this.keepAliveTime = keepAliveTime;
        if (keepAliveTime > 0) {
            allowThreadTimeOut = true;
        }
        this.workQueue = new ArrayBlockingQueue<>(5);
    }

    @Override
    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("任务不能为空");
        }
        if (isShutdown) {
            throw new IllegalStateException("线程池已经关闭");
        }
        int c = atomicInteger.get();
        //创建核心线程数
        if (c < poolSize) {
            if (addWorker(task, true)) {

            }
        } else if (workQueue.offer(task)) {

        } else {
            policyHandler.rejected(task, this);
        }
    }

    @Override
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            isShutdown = true;
            for (Worker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.isInterrupted();
                    } catch (Exception e) {

                    } finally {
                        w.unlock();
                    }
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    @Override
    public int getActiveThread() {
        return atomicInteger.get();
    }

    @Override
    public Runnable getTask() {
        try {
            return allowThreadTimeOut ? workQueue.poll(keepAliveTime, TimeUnit.MILLISECONDS) : workQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean addWorker(Runnable r, boolean startNew) {
        if (startNew) {
            atomicInteger.incrementAndGet();
        }
        boolean workAdded = false;
        boolean wordStarted = false;
        Worker w = new Worker(r);
        Thread t = w.thread;
        if (t != null) {
            ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if (!isShutdown) {
                    if (t.isAlive()) {
                        throw new IllegalThreadStateException("线程处于运行状态");
                    }
                    workers.add(w);
                    workAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if (workAdded) {
                //添加成功启动线程
                t.start();
                wordStarted = true;
            }
        }
        return wordStarted;
    }

    private void runWorker(Worker worker) {
        Thread wt = Thread.currentThread();
        Runnable task = worker.firstTask;
        worker.firstTask = null;
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                worker.lock();
                if (isShutdown && !wt.isInterrupted()) {
                    wt.interrupt();
                }
                try {
                    task.run();
                } finally {
                    task = null;
                    worker.completedTask++;
                    worker.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(worker, completedAbruptly);
        }

    }

    private void processWorkerExit(Worker worker, boolean completedAbruptly) {
        if (completedAbruptly) {
            atomicInteger.decrementAndGet();
        }
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completeTaskCount += worker.completedTask;
            workers.remove(worker);
        } finally {
            mainLock.unlock();
        }
        if (completedAbruptly && !workQueue.isEmpty()) {
            addWorker(null, false);
        }
    }

    static AtomicInteger atomic = new AtomicInteger();

    class Worker extends ReentrantLock implements Runnable {
        volatile long completedTask;
        final Thread thread;
        Runnable firstTask;

        public Worker(Runnable r) {
            this.firstTask = r;
            this.thread = new Thread(this, "thread-name" + atomic.get());
        }

        @Override
        public void run() {
            runWorker(this);
        }
    }
}
