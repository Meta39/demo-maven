package com.fu.redisdemo;

import com.sun.istack.internal.NotNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class LockTest {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String LOCK = "lock:";
    private static final Long TIMEOUT = 30L;

    //加锁。建议key用id，否则全局公用一个key阻塞，锁粒度太大。
    @SneakyThrows
    public Boolean lock(@NotNull String key, Long timeout) {
        if (key == null || key.trim().isEmpty()){
            throw new Exception("加锁时key不能为空！");
        }
        if (timeout == null || timeout == 0L){
            timeout = TIMEOUT;
        }
        String uuid = UUID.randomUUID().toString();
        return this.redisTemplate.opsForValue().setIfAbsent(LOCK + key, uuid, timeout, TimeUnit.SECONDS);
    }

    //解锁
    @SneakyThrows
    public Boolean unLock(@NotNull String key) {
        if (key == null || key.trim().isEmpty()){
            throw new Exception("解锁时key不能为空！");
        }
        return this.redisTemplate.delete(LOCK + key);
    }

    //阻塞
    @SneakyThrows
    public Boolean waitAndGetLock(@NotNull String key, Long timeout) {
        if (key == null || key.trim().isEmpty()){
            throw new Exception("阻塞时key不能为空！");
        }
        if (timeout == null || timeout == 0L){
            timeout = TIMEOUT;
        }
        Boolean hasLock;
        do {
            hasLock = this.lock(key, timeout);
            if (hasLock != null && hasLock) {
                break;
            }
        } while (true);
        return true;
    }

    @Test
    public void doOne() throws InterruptedException {
        //模拟会员挪动
        try {
            this.lock("publicKey", 30L);
            System.out.println("模拟会员挪动......");
            Thread.sleep(30000);
        } finally {
            this.unLock("publicKey");
        }
    }

    @Test
    public void doTwo() {
        try {
            this.waitAndGetLock("publicKey", 30L);
            System.out.println("模拟奖励发放......");
        }finally {
            this.unLock("publicKey");
        }
    }

}
