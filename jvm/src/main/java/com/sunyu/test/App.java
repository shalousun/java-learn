package com.sunyu.test;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

/**
 * 打印对象布局
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Person person = new Person();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
    }
}
