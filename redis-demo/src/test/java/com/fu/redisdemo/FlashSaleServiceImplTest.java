package com.fu.redisdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static com.fu.redisdemo.service.impl.FlashSaleServiceImpl.INVENTORY;

@SpringBootTest
public class FlashSaleServiceImplTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置某个商品的库存数量
     */
    @Test
    public void test1() {
        this.redisTemplate.opsForValue().set(INVENTORY, 5000);
    }

}
