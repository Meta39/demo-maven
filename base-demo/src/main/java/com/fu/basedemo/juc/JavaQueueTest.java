package com.fu.basedemo.juc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * java内置消息队列
 */
public class JavaQueueTest {

    @Test
    public void test() throws InterruptedException {
        /*
        数组和链表的选择：
        数组：有序，读多写少
        链表：无序，写多读少
         */

//ArrayBlockingQueue是基于数组的阻塞队列实现，其内部维护了一个定长数组，以便缓存队列中的数据对象。其除了定长数组以外，ArrayBockingQueue内部还保存着两个整形变量，分分别标识着队列的头部和尾部再数据中的位置。
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        //添加元素
        arrayBlockingQueue.add("a");//不推荐，直接返回true/false，队列满的时候会抛出异常
        arrayBlockingQueue.put("b");//不推荐，队列满时，等待可用空间，无返回值
        arrayBlockingQueue.offer("c");//推荐，队列满时，不等待，直接返回true/false
        arrayBlockingQueue.offer("d", 3, TimeUnit.SECONDS);//推荐，队列满时，在指定等待时间等待可用空间，超过时间还没有空间，返回false
        //获取/删除元素
        System.out.println();
        System.out.println("源队列：" + arrayBlockingQueue);
        System.out.println("contains是否包含指定元素，返回true/false：" + arrayBlockingQueue.contains("a"));
        System.out.println("take返回第一个元素并删除，元素：" + arrayBlockingQueue.take() + "，删除第一个元素以后的队列：" + arrayBlockingQueue);//推荐，返回第一个元素并删除该元素，如果没有元素则会一直等待
        System.out.println("poll返回第一个元素并删除，元素：" + arrayBlockingQueue.poll() + "，删除第一个元素以后的队列：" + arrayBlockingQueue);//推荐，返回第一个元素并删除该元素，队列为空返回null
        System.out.println("peek返第一个元素但不删除，元素：" + arrayBlockingQueue.peek() + "，现队列：" + arrayBlockingQueue);//不推荐，返回第一个元素但不删除
        //不推荐，按适当顺序返回所有元素数组，因为是Object需要强转
        for (Object o : arrayBlockingQueue.toArray()) {
            System.out.println("Object[]元素：" + o);
        }
        //推荐，按适当顺序返回所有指定类型元素数组，因为指定类型，不需要强转
        for (Object o : arrayBlockingQueue.toArray(new String[]{})) {
            System.out.println("T[]元素：" + o);
        }
        System.out.println("toString：" + arrayBlockingQueue.toString());//转成字符串
        //获取队列元素数量
        System.out.println("remainingCapacity在无阻塞的情况下返回元素数量：" + arrayBlockingQueue.remainingCapacity());//不推荐，只在无阻塞的情况下返回元素数量
        System.out.println("size直接返回元素数量：" + arrayBlockingQueue.size());//推荐，直接返回元素数量
        //删除元素
        System.out.println("remove移除指定元素，移除元素：" + arrayBlockingQueue.remove("c") + "，现队列：" + arrayBlockingQueue);//推荐，移除指定元素
        //移除元素
        arrayBlockingQueue.clear();//推荐，自动移除所有元素
        System.out.println("clear()清空队列：" + arrayBlockingQueue);
        arrayBlockingQueue.drainTo(new ArrayList<>());//不推荐，移除此队列中所有可用的元素并将他们添加到指定的Collection中
        arrayBlockingQueue.drainTo(new ArrayList<>(), 10);//推荐，移除此队列中指定数量的元素并将他们添加到指定的Collection中


//LinkedBlockingQueue是基于链表的阻塞队列,其内部维持着数据缓存的对象是一个链表构成。当生产者往队列中放入一个数据时，队列会从生产者中获取数据，并缓存再队列内部，而生产者立即返回。
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        //LinkedBlockingQueue的方法和上面的ArrayBlockingQueue的方法是一样的，只是它是使用链表，上面用的是数组。
        linkedBlockingQueue.offer("A");
    }
}
