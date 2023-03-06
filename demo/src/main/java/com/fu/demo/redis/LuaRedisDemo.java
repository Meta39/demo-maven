package com.fu.demo.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LuaRedisDemo {
    private RedisTemplate redisTemplate;

    public void LuaOperateRedis(){
        //除了下面这个是原子性的，其它都要分2步。
        redisTemplate.opsForValue().set("key","value",120, TimeUnit.SECONDS);//（推荐）


        //非lua操作redis（不推荐）（因为设置值和过期时间是分开的，不能保证原子性）
        redisTemplate.opsForHash().put("key","item","value");
        redisTemplate.expire("key",120,TimeUnit.SECONDS);


        //lua（推荐）（设置值和过期时间一起，保证原子性）
        String script = "redis.call('HSET',KEYS[1],KEYS[2],ARGV[1]) redis.call('EXPIRE',KEYS[1],ARGV[1])";
        // 实例化脚本对象
        DefaultRedisScript<Boolean> lua = new DefaultRedisScript<>();
        // 设置脚本的返回值
        lua.setResultType(Boolean.class);
        lua.setScriptText(script);
        // key的集合
        List<String> keys = Arrays.asList("key","item");
        // 执行lua脚本
        redisTemplate.execute(lua, keys, 120);
    }
}
