package com.fu.basedemo.designpattern;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 单例模式
 */
public class SingleMode {

    /**
     * 饿汉式：优点写法简单，较早在内存加载，使用更方便、更快，更安全。缺点是在内存中占用时间较长。
     * Runtime类就是一个典型的饿汉式单例模式。
     */
    //1.类的构造器私有化，必须声明为private
    private SingleMode(){

    }

    //2.在类内部创建静态的当前类实例，必须声明为private static final
    private static final SingleMode singleMode = new SingleMode();

    //3.使用getXxx()获取当前类的实例，必须声明为public static
    public static SingleMode getSingleMode(){
        return singleMode;
    }

}

/**
 * 懒汉式（不推荐）：优点是在需要的时候创建，节省内存空间。缺点是线程不安全（有解决方案）
 *
 */
class LazySingleMode {

    private LazySingleMode(){

    }

    private static LazySingleMode lazySingleMode = null;

    public static LazySingleMode getLazySingleMode(){
        //如果没有创建对象，则创建。因此多线程情况下线程不安全，有可能创建多个对象。（这个后面会解决）
        if (lazySingleMode == null){
            lazySingleMode = new LazySingleMode();
        }
        return lazySingleMode;
    }

}

/**
 * 懒汉式：通过加悲观锁synchronized让线程安全
 */
class LazySynchronizedSingleMode {

    private LazySynchronizedSingleMode(){

    }

    private static LazySynchronizedSingleMode lazySynchronizedSingleMode = null;

    //加synchronized锁让线程安全
    public static LazySynchronizedSingleMode getLazySynchronizedSingleMode(){
        if (lazySynchronizedSingleMode == null){
            synchronized (LazySynchronizedSingleMode.class){
                if (lazySynchronizedSingleMode == null){
                    lazySynchronizedSingleMode = new LazySynchronizedSingleMode();
                }
            }
        }
        return lazySynchronizedSingleMode;
    }

}

/**
 * 懒汉式：通过加ReentrantLock锁让线程安全（推荐）
 */
class LazyReentrantLockSingleMode{

    private static final ReentrantLock reentrantLock = new ReentrantLock();//使用ReentrantLock必须声明static final

    private LazyReentrantLockSingleMode(){

    }

    private static LazyReentrantLockSingleMode lazyReentrantLockSingleMode = null;

    public static LazyReentrantLockSingleMode getLazyReentrantLockSingleMode(){
        if (lazyReentrantLockSingleMode == null){
            reentrantLock.lock();//加锁放到try外面
            try {
                lazyReentrantLockSingleMode = new LazyReentrantLockSingleMode();
            }finally {
                //必须在finally解锁，防止死锁，可以没有catch
                reentrantLock.unlock();
            }
        }
        return lazyReentrantLockSingleMode;
    }

}
