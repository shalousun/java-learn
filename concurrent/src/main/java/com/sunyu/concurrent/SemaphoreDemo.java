package com.sunyu.concurrent;

import java.util.concurrent.Semaphore;


/**
 * 信号量测试
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //创建两个信号量
        Semaphore semaphore = new Semaphore(3);
        Person p1 = new Person(semaphore,"A");
        p1.start();
        Person p2 = new Person(semaphore,"B");
        p2.start();
        Person p3 = new Person(semaphore,"C");
        p3.start();
    }
}

class Person extends Thread{
    private Semaphore semaphore;
    public Person(Semaphore semaphore,String name){
        setName(name);
        this.semaphore = semaphore;
    }
    public void run(){
        System.out.println(this.getName()+" is waiting");
        try{
            semaphore.acquire();
            System.out.println(this.getName()+" is serving!");
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(this.getName()+" is done");
        semaphore.release();
    }
}