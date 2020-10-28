package com.sunyu.concurrent.skiplist;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapDemo {

    static Map<String,Object> map0 = new ConcurrentHashMap<>();

    static Map<Integer,Integer> map = new ConcurrentSkipListMap<>();

    public static class Task implements Runnable{
        int start = 0;
        public Task(int start){
            this.start = start;
        }
        @Override
        public void run() {
            for(int i = start;i< 100000;i+=2){
                map.put(i,i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Task(0));
        Thread t2 = new Thread(new Task(1));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(map.size());
  //        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
//            System.out.println(entry.getKey());
//        }
    }
}
