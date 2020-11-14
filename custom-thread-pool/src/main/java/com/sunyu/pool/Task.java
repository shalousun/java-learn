package com.sunyu.pool;

/**
 * @author yu 2020/11/14.
 */
public class Task implements Runnable {
    private int nov;

    public Task(int i){
        this.nov = i;
    }
    @Override
    public void run() {
        System.out.println("当前执行任务的线程是："+Thread.currentThread().getName());
        System.out.println("我是任务"+nov+"我在执行...");
    }
}
