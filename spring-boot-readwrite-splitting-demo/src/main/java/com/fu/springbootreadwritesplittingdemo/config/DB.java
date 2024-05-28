package com.fu.springbootreadwritesplittingdemo.config;

/**
 * 创建日期：2024-05-28
 */
public enum DB {
    MASTER, SLAVE;

    public static final DB[] VALUES = values();
    public static final int VALUES_LENGTH = VALUES.length;

    // 根据下标获取枚举元素的方法
    public static DB getByIndex(int index) {
        if (index < 0 || index >= VALUES_LENGTH) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + VALUES_LENGTH);
        }
        return VALUES[index];
    }

}
