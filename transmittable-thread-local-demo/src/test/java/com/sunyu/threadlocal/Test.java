package com.sunyu.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testThreadLocal1();
//        testThreadLocal2();
        testThreadLocal3();
    }

    private static void testThreadLocal1() throws InterruptedException, ExecutionException {
        final ThreadLocal<String> local = new java.lang.InheritableThreadLocal<>();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 20; i++) {
            local.set(i + "");
            System.out.println(local.get());
            Future<?> future = executorService.submit(new Runnable() {

                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + local.get());
                    local.set(null);
                }
            });
            future.get();
            System.out.println(local.get());
            local.set(null);
        }
    }

    private static void testThreadLocal2() throws InterruptedException, ExecutionException {
        ThreadLocal<String> local = new java.lang.InheritableThreadLocal<>();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 20; i++) {
            local.set(i + "");
            System.out.println(local.get());
            Future<?> future = executorService.submit(new ParamRunnable(i + ""));
            future.get();
            System.out.println(local.get());
            local.set(null);
        }
    }

    private static void testThreadLocal3() throws InterruptedException, ExecutionException {
        final TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 20; i++) {
            context.set(i + "");
            System.out.println(context.get());
            Future<?> future = executorService.submit(TtlRunnable.get(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + context.get());
                    context.set(null);
                }
            }));
            future.get();
            System.out.println(context.get());
            context.set(null);
        }
    }

    private static class ParamRunnable implements Runnable {

        private String param;

        public ParamRunnable(String param) {
            this.param = param;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":" + param);
        }

    }
}
