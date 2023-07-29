package com.fu.redisdemo.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//@Component //引入DistributedLockFactory工厂模式，从工厂获取即可
public class RedisDistributedLock implements Lock {
    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);
    private RedisTemplate<String, Object> redisTemplate;
    private static final String LUA_LOCK_SCRIPT = "if redis.call('exists',KEYS[1]) == 0 or redis.call('hexists',KEYS[1],ARGV[1]) == 1 then redis.call('hincrby',KEYS[1],ARGV[1],1) redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";
    private static final String LUA_UNLOCK_SCRIPT = "if redis.call('hexists',KEYS[1],ARGV[1]) == 0 then return nil elseif redis.call('hincrby',KEYS[1],ARGV[1],-1) == 0 then return redis.call('del',KEYS[1]) else return 0 end";
    private static final String RENEW_EXPIRE_TIME = "if redis.call('hexists',KEYS[1],ARGV[1]) == 1 then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end";
    private String redisLockName; //redis key即KEYS[1]
    private String uuidValue; //redis value即ARGV[1]
    private long expireTime; //过期时间即ARGV[2]

    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate, String redisLockName, String uuid) {
        this.redisTemplate = redisTemplate;
        this.redisLockName = redisLockName;
        this.uuidValue = uuid + ":" + Thread.currentThread().getId();
        this.expireTime = 30L;
    }

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public boolean tryLock() {
        try {
            tryLock(-1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("RedisDistributedLock：", e);
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit timeUnit) throws InterruptedException {
        if (time == -1L) {
//            log.info("==========================测试可重入锁===============================");
//            log.info("LOCK：lockName:{}，uuidValue:{}",redisLockName,uuidValue);
            while (Boolean.FALSE.equals(redisTemplate.execute(new DefaultRedisScript<>(LUA_LOCK_SCRIPT, Boolean.class), Arrays.asList(redisLockName), uuidValue, expireTime))) {
                Thread.sleep(60);
            }
            //过期时间续期
            renewExpire();
            return true;
        }
        return false;
    }

    @Override
    public void unlock() {
//        log.info("UNLOCK：lockName:{}，uuidValue:{}",redisLockName,uuidValue);
        Long flag = redisTemplate.execute(new DefaultRedisScript<>(LUA_UNLOCK_SCRIPT, Long.class), Arrays.asList(redisLockName), uuidValue);
        if (flag == null) {
            throw new RuntimeException("RedisDistributedLock当中的锁不存在");
        }
    }

    //===============暂时用不到
    @Override
    public void lockInterruptibly() {}

    @Override
    public Condition newCondition() {
        return null;
    }


    //==============================私有方法================================

    /**
     * 递归续期，直到业务完成。
     */
    private void renewExpire() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Boolean.TRUE.equals(redisTemplate.execute(new DefaultRedisScript<>(RENEW_EXPIRE_TIME, Boolean.class), Arrays.asList(redisLockName), uuidValue, expireTime))){
                    renewExpire();
                }
            }
        }, (this.expireTime * 1000) / 3);
    }

}
