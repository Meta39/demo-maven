package com.fu.redisdemo.util;

import com.fu.redisdemo.config.RedissonBusiness;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RedissonUtils {
    private static final Logger log = LoggerFactory.getLogger(RedissonUtils.class);

    /**
     * 自动加锁和释放锁公共方法
     * @param redisson RedissonClient
     * @param lockName 分布式锁的名称
     * @param business 业务逻辑
     * @return 是否加锁成功
     */
    public static boolean autoLock(RedissonClient redisson, String lockName, RedissonBusiness business) {
        RLock lock = redisson.getLock(lockName);
        boolean tryLock = lock.tryLock();
        if (tryLock) {
            try {
                business.business();
                return true;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            } finally {
                lock.unlock();
            }
        }
        return false;
    }

}
