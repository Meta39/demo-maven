package com.fu.redisdemo;

import com.fu.redisdemo.entity.RedisObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class RedisTemplateTest {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test(){
        RedisObject r = new RedisObject();
        r.setId(1);
        r.setUsername(Arrays.asList("用户名1","用户名2"));
        this.redisTemplate.opsForValue().set("1", r);
        this.redisTemplate.opsForValue().set("abc", r);
        RedisObject ro = (RedisObject) this.redisTemplate.opsForValue().get("1");
        System.out.println(ro);
    }
}
