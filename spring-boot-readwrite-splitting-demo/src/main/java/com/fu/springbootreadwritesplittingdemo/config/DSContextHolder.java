package com.fu.springbootreadwritesplittingdemo.config;

public class DSContextHolder {

    private static final ThreadLocal<DB> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void set(DB db) {
        CONTEXT_HOLDER.set(db);
    }

    public static DB get() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
