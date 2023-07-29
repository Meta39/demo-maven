package com.fu.basedemo.juc.async;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步调用：分为有返回值和无返回值
 */
public class AsyncTests<T> {

    //无返回值
    @Test
    public void test() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> asyncVoid = CompletableFuture.runAsync(() -> System.out.println(Thread.currentThread().getName() + "无返回值的异步调用"));
        asyncVoid.get();
    }

    //有返回值
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> asyncReturn = CompletableFuture.supplyAsync(() ->{
            System.out.println(Thread.currentThread().getName() + "有返回值的异步调用");
            //模拟异常
//            int i = 1/0;
            return 1;
        });
        //value是返回值，exception是抛出异常。value和exception互斥。
        asyncReturn.whenComplete((value,exception) ->{
            System.out.println("t======="+value);
            System.out.println("u======="+exception);
        }).get();
    }
}
