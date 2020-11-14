package com.sunyu.test;

import com.sunyu.pool.CustomBlockingQueue;
import com.sunyu.pool.CustomThreadPool;
import com.sunyu.pool.CustomThreadPoolExecutor;
import com.sunyu.pool.factory.CustomThreadFactory;
import com.sunyu.pool.handler.CallerRunsPolicy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yu 2020/11/14.
 */
public class CustomThreadPoolTest {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);
        //创建一个核心线程数为1，最大线程数为3，大小为1的队列的线程池
        CustomThreadPool pool = new CustomThreadPoolExecutor(1, 3,
                new CustomBlockingQueue(1, 1l, TimeUnit.SECONDS), new CallerRunsPolicy(),
                new CustomThreadFactory());

        /**
         * 提交5个任务，这个任务睡眠两秒后打印当前的线程和任务名
         */
        for (int j = 0; j < 5; j++) {
            String taskname = " run task" + j;
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + taskname + " start");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + taskname + " end");

            });
        }
    }
}
