package com.fu.basedemo.juc;

import java.util.concurrent.Callable;

/**
 * 多线程之实现Callable接口，重写call方法，该方法有返回值。
 */
public class JavaThreadImplCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("多线程之实现Runnable接口，重写call方法，该方法有返回值。返回值的类型自定义");
        return 1;
    }
}
