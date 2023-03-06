package com.fu.demo.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CaffeineDemo {
    private static final Logger log = LoggerFactory.getLogger(CaffeineDemo.class);

    /**
     * 驱逐：缓存元素因为策略被移除
     * 失效：缓存元素被手动移除
     * 移除：由于驱逐或者失效而最终导致的结果
     */
    public static void main(String[] args) throws InterruptedException {
        //自动加载元素到缓存当中
        LoadingCache<Object, Object> cache = Caffeine.newBuilder()
                //最大内存大小，等价于10000
                .maximumSize(10_000)
                //写入到内存多久以后过期
                .expireAfterWrite(3, TimeUnit.SECONDS)
                //我直接返回null了，不想构建，这里应为对应的一个k -> createExpensiveGraph(key)方法
                .build(k -> null);
        //添加或者更新一个缓存元素
        cache.put("name", "Meta");
        cache.put("name2", "Meta39");
        Thread.sleep(2000);
        //查找一个缓存元素， 没有查找到的时候返回null
        log.info("{}",cache.getIfPresent("name"));
        //查找缓存，如果缓存不存在则生成缓存元素,  如果无法生成则返回null（比getIfPresent多了一步）
        log.info("name，{}", cache.get("name"));
        log.info("name2，{}", cache.get("name2"));
        //移除，不管时间到没到都会移除。也可以用监听器去监听移除的内容
        cache.invalidate("name");
        log.info("name已被移除，{}", cache.get("name"));
        log.info("name2未被移除，{}", cache.get("name2"));
        // 移除所有key
        cache.invalidateAll();
    }
}
