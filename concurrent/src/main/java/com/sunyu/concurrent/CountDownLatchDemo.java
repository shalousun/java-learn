package com.sunyu.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Racer racer1 = new Racer(countDownLatch,"A");
        racer1.start();
        Racer racer2 = new Racer(countDownLatch,"B");
        racer2.start();
        Racer racer3 = new Racer(countDownLatch,"C");
        racer3.start();

        for(int i = 0;i<3;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
            countDownLatch.countDown();
            if(i==2){
                System.out.println(" start");
            }

        }
    }
}

class Racer extends Thread{

    private CountDownLatch countDownLatch;

    public Racer(CountDownLatch countDownLatch,String name){
        setName(name);
        this.countDownLatch = countDownLatch;

    }
    public void run(){
        System.out.println(this.getName()+" is waiting");
        try{
            countDownLatch.await();
            for(int i = 0;i<3;i++){
                System.out.println(this.getName()+ " "+i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}