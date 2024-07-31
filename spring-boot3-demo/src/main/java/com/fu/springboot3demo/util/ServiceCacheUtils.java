package com.fu.springboot3demo.util;

import com.fu.springboot3demo.dto.ServiceInfo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存创建的 bean
 * @since 2024-07-16
 */
public abstract class ServiceCacheUtils {
    private ServiceCacheUtils() {
    }

    public static ConcurrentHashMap<String, ServiceInfo<?>> cache = new ConcurrentHashMap<>();

}
