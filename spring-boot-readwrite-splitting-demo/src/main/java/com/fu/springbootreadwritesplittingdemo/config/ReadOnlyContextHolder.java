package com.fu.springbootreadwritesplittingdemo.config;

public class ReadOnlyContextHolder {

    private static final ThreadLocal<SlaveDB> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void set(SlaveDB db) {
        CONTEXT_HOLDER.set(db);
    }

    public static SlaveDB get() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
