package com.fu.redisdemo;

import com.fu.redisdemo.lock.DistributedLockFactory;
import com.fu.redisdemo.lock.DistributedType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.Lock;

/**
 * RedisDistributedLock是分布式可重入阻塞锁，和java的ReentrantLock相同，只不过一个是jvm锁，一个是分布式锁。
 */
@SpringBootTest
public class RedisDistributedLockTests {
    @Autowired
    private DistributedLockFactory distributedLockFactory;

    /**
     * 阻塞test2 15s
     */
    @Test
    public void test(){
        Lock redisDistributedLock = distributedLockFactory.getDistributedLock(DistributedType.Redis);
        redisDistributedLock.lock();
        try {
            System.out.println("我要阻塞test2");
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            redisDistributedLock.unlock();
        }
    }

    /**
     * 被test阻塞15s
     */
    @Test
    public void test2(){
        Lock redisDistributedLock = distributedLockFactory.getDistributedLock(DistributedType.Redis);
        redisDistributedLock.lock();
        try {
            System.out.println("我被test阻塞15秒");
        }finally {
            redisDistributedLock.unlock();
        }
    }

}
