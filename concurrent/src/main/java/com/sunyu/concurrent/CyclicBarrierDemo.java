package com.sunyu.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // 创建循环屏障，条件满足时就执行runnable
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("Game start...");
            }
        });

        // 有A,B,C三个玩家
        new Player(cyclicBarrier, "A").start();
        new Player(cyclicBarrier, "B").start();
        new Player(cyclicBarrier, "C").start();
    }
}

class Player extends Thread {
    private CyclicBarrier cyclicBarrier;

    public Player(CyclicBarrier cyclicBarrier,String name) {
        setName(name);
        this.cyclicBarrier = cyclicBarrier;
    }
    @Override
    public void run() {
        System.out.println(getName() + " is waiting other player....");
        try {
            // 屏障处等待
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}