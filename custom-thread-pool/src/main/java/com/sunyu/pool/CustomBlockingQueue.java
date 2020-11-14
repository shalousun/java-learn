package com.sunyu.pool;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yu 2020/11/14.
 */
public class CustomBlockingQueue implements CustomQueue{
    private final ReentrantLock lock = new ReentrantLock();
    //任务队列
    private final LinkedList<Runnable> queue = new LinkedList<>();
    private int size;
    private long timeOut;

    /**
     * 空的条件队列
     */
    private final Condition empty;

    /**
     * 满的条件队列
     */
    private final Condition full;


    /**
     * 构造方法
     *
     * @param capacity
     * @param keepAliveTime
     * @param unit
     */
    public CustomBlockingQueue(int capacity, long keepAliveTime,
                  TimeUnit unit) {
        this.size = capacity;
        empty = lock.newCondition();
        full = lock.newCondition();
        this.timeOut = unit.toNanos(keepAliveTime);//超时时间
    }

    /**
     * 存一个任务
     *
     * @param run
     * @return
     */
    @Override
    public boolean offer(Runnable run) {
        if (run == null) {
            throw new NullPointerException();
        }

        lock.lock();
        try {
            long n = timeOut;
            //队列满了则睡眠等待
            while (size == queue.size()) {
                if (n <= 0) {
                    //超时则入队失败
                    return false;
                }
                //如果还没到时间则继续睡眠
                n = full.awaitNanos(n);
            }

            queue.addLast(run);
            //唤醒一个线程告诉它可以从队列里面取任务了
            empty.signal();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    /**
     * 取一个任务
     *
     * @return
     */
    @Override
    public Runnable poll() {
        lock.lock();

        try {
            long n = timeOut;
            while (queue.isEmpty()) {
                if (n <= 0) {
                    //超时则返回null，返回null后工作线程就会被回收掉
                    return null;
                }
                System.out.println(Thread.currentThread().getName() + "队列为空，睡眠等待");
                //如果还没到时间则继续睡眠
                n = empty.awaitNanos(n);
            }

            //取出一个任务返回执行
            Runnable task = queue.removeFirst();
            //唤醒一个线程，告诉它可以往队列里面存了
            full.signal();
            return task;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;
    }

    /**
     * 队列的大小
     *
     * @return
     */
    @Override
    public int size() {
        return queue.size();
    }
}
