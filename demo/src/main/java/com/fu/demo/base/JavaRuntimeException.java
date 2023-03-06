package com.fu.demo.base;

/**
 * 运行时异常，继承RuntimeException，编译通过，需要程序员自己在代码里面throw new RuntimeException()
 */
public class JavaRuntimeException extends RuntimeException{

    private static final long serialVersionUID = 8451676454861340334L;

    private String name;
    private String msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
