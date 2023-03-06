package com.fu.demo.controller;

import com.fu.demo.config.NotCheckConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RoundRobinController {
    @Resource
    private NotCheckConfig notCheckConfig;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 轮询获取yml配置的list
     *
     * @return
     */
    @GetMapping("roundRobin")
    public String roundRobin() {
        String INDEX = "index";
        if (!redisTemplate.hasKey(INDEX)) {//如果key不存在就创建key,并设置下标初始值为0
            redisTemplate.opsForValue().set(INDEX, 0);
        }
        int index = redisTemplate.opsForValue().get(INDEX);//有下标则直接获取
        List<String> list = notCheckConfig.getNotCheck();//获取yml配置的集合
        if (index >= list.size()) {//超过list集合的值就重新赋值（轮询）
            index = 0;
        }
        String ip = list.get(index);//获取集合中第index个元素的值，每次调用获取list中的第index条数据
        redisTemplate.opsForValue().set(INDEX, ++index);//利用redis单线程的特性存放全局index下标
        return ip;
    }
}
