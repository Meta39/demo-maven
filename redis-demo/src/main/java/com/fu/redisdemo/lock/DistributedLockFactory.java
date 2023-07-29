package com.fu.redisdemo.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.locks.Lock;

@Component
public class DistributedLockFactory {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private String lockName;
    private String uuid;

    public DistributedLockFactory() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Lock getDistributedLock(DistributedType lockType) {
        if (lockType == null) {
            return null;
        }
        if (lockType == DistributedType.Redis) {
            this.lockName = "RedisLock";
            return new RedisDistributedLock(redisTemplate, lockName,uuid);
        } else if (lockType == DistributedType.MySQL) {
            //TODO MySQL分布式锁
            this.lockName = "MySQLLock";
            return null;
        }else if (lockType == DistributedType.Zookeeper) {
            //TODO Zookeeper分布式锁
            this.lockName = "ZookeeperLock";
            return null;
        }
        return null;
    }
}
