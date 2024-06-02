package com.fu.springbootreadwritesplittingdemo.config;

public class SlaveDBThreadLocal {

    private static final ThreadLocal<SlaveDB> SLAVE_DB_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(SlaveDB db) {
        SLAVE_DB_THREAD_LOCAL.set(db);
    }

    public static SlaveDB get() {
        return SLAVE_DB_THREAD_LOCAL.get() == null ? null : SLAVE_DB_THREAD_LOCAL.get();
    }

    public static void remove() {
        SLAVE_DB_THREAD_LOCAL.remove();
    }

}
