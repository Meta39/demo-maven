package com.fu.basedemo.juc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 线程安全的HashMap之ConcurrentHashMap
 */
public class JavaConcurrentHashMapTest {
    /**
     * 高并发情况下，线程不安全
     */
    private static final Map<String,String> map = new HashMap<>();
    /**
     * 高并发情况下，线程安全
     */
    private static final ConcurrentMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    @Test
    public void test() {
        map.put("key","vale");
        System.out.println("HashMap："+map.get("key"));
        concurrentHashMap.put("key","value");
        System.out.println("ConcurrentHashMap：" + concurrentHashMap.get("key"));
    }

}
