package com.sunyu.aqs.lock;

import sun.misc.Unsafe;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.LockSupport;

/**
 * @author yu 2020/8/12.
 */
public class CustomLock {

    private volatile int state = 0;

    private Thread lockHolder;

    private final ConcurrentLinkedDeque<Thread> linkedDeque = new ConcurrentLinkedDeque<>();

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }

    public boolean acquire() {
        Thread current = Thread.currentThread();
        int state = getState();
        if (state == 0) { //可以加锁
            ConcurrentLinkedDeque<Thread> q = this.linkedDeque;
            if ((q.size() == 0||current== linkedDeque.peek()) && compareAndSwapState(0, 1)) {
                setLockHolder(current);
                return true;
            }
        }
        return false;
    }

    /**
     * 加锁
     */
    public void lock() {
        if (acquire()) {
            return;
        }
        //加锁失败
        Thread current = Thread.currentThread();
        //排队
        linkedDeque.add(current);
        //自旋
        for (; ; ) {
            if (linkedDeque.peek() == current && acquire()) {
                linkedDeque.poll();//线程被唤醒
                return;
            }
            //阻塞
            LockSupport.park();
        }

    }

    /**
     * 释放锁
     */
    public void unlock() {
        Thread current = Thread.currentThread();
        if (current != lockHolder) {
            throw new RuntimeException("不能释放锁");
        }
        int c = getState();
        if (compareAndSwapState(1, 0)) { //释放锁
            setLockHolder(null);
            Thread thread = linkedDeque.peek();//唤醒对头
            if (thread != null) {
                LockSupport.unpark(thread);
            }
        }
    }


    /**
     * 比较交换
     *
     * @param except
     * @param update
     * @return
     */
    public final boolean compareAndSwapState(int except, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, except, update);
    }

    private static final Unsafe unsafe = Unsafe.getUnsafe();

    private static final long stateOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(CustomLock.class.getField("state"));
        } catch (Exception e) {
            throw new RuntimeException("");
        }
    }
}
