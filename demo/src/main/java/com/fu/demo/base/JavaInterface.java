package com.fu.demo.base;

/**
 * 接口
 * 接口是抽象类的延伸，在 Java 8 之前，它可以看成是一个完全抽象的类，也就是说它不能有任何的方法实现。
 */
public interface JavaInterface {

    void needToImpl();

    /**
     * java8新特性，使用default关键字让接口有默认方法
     */
    default void print(){
        //尽量不要用System.out.println();因为有synchronized锁
        System.out.println("java8允许接口有默认方法");
    }

}
