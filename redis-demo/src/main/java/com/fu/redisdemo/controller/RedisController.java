package com.fu.redisdemo.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 新增/修改数据
     * @param key 键
     * @param value 值
     * @param timeout 过期时间（单位：秒s）
     */
    @PostMapping
    public void insert(@RequestParam(name = "key") String key,
                       @RequestParam(name = "value") String value,
                       @RequestParam(name = "timeout") Long timeout) {
        this.redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 根据key查询数据
     * @param key 键
     * @return
     */
    @GetMapping("{key}")
    public String select(@PathVariable String key){
        return (String) this.redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key删除数据
     * @param key 键
     */
    @DeleteMapping("{key}")
    public void delete(@PathVariable String key){
        this.redisTemplate.delete(key);
    }

}
