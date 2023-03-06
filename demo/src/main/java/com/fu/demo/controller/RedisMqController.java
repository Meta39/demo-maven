package com.fu.demo.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Redis消息队列
 * 单体应用可以使用Redis作为消息队列，但是单体应用jdk自带的消息队列也是不错的。
 * 如果作为全局消息队列，那肯定redis会更好，如果是类消息队列，jdk自带的消息队列会更好。
 */
@RestController
@RequestMapping("redisMq")
public class RedisMqController {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    private static final String MESSAGE_KEY = "message:queue";

    /**
     * 向Redis发送消息
     * @param message 消息内容
     * @return
     */
    @GetMapping("send/{message}")
    public Long send(@PathVariable String message){
        return redisTemplate.opsForList().leftPush(MESSAGE_KEY,message);
    }

    /**
     * 根据消息Key向Redis获取消息
     * @param messageKey 消息键名称
     * @return
     */
    @GetMapping("get/{messageKey}")
    public String get(@PathVariable String messageKey){
        return redisTemplate.opsForList().rightPop(messageKey);
    }

}
