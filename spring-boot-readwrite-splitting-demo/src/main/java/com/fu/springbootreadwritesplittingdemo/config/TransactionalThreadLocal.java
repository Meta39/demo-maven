package com.fu.springbootreadwritesplittingdemo.config;

/**
 * 创建日期：2024-05-30
 */
public class TransactionalThreadLocal {
    private static final ThreadLocal<Boolean> IS_TRANSACTION = new ThreadLocal<>();

    public static void set(Boolean value) {
        IS_TRANSACTION.set(value);
    }
    public static Boolean get() {
        return IS_TRANSACTION.get() != null && IS_TRANSACTION.get();
    }

    public static void remove() {
        if (get()) {
            IS_TRANSACTION.remove();
        }
    }
}
