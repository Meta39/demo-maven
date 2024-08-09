package com.fu.springboot3demo.generic;

/**
 * 通用方法接口
 * @since 2024-07-16
 */
public interface GenericService<T> {
    Object invoke(T req);
}