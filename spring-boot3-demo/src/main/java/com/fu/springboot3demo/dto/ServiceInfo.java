package com.fu.springboot3demo.dto;

import com.fu.springboot3demo.service.GenericService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 缓存存储反射调用的类和请求参数、返回参数
 * @since 2024-07-16
 */
@Getter
@AllArgsConstructor
public class ServiceInfo<T> {
    private final GenericService<T> service;
    private final Method method;
    private final Class<T> requestType;

    public Object invoke(Object requestObject) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(service, requestObject);
    }
}