package com.fu.demo.base.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 线程安全的HashMap之ConcurrentHashMap
 */
@Slf4j
public class JavaConcurrentHashMap{
    /**
     * 高并发情况下，线程不安全
     */
    private static final Map<String,String> map = new HashMap<>();
    /**
     * 高并发情况下，线程安全
     */
    private static final ConcurrentMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        map.put("key","vale");
        log.info("HashMap：{}",map.get("key"));
        concurrentHashMap.put("key","value");
        log.info("ConcurrentHashMap{}",concurrentHashMap.get("key"));
    }

}
