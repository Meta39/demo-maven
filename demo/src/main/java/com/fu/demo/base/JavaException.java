package com.fu.demo.base;

/**
 * 非运行时异常，继承Exception，编译不通过，需要程序员自行在方法()throws Exception，方法里面throw new Exception()。
 */
public class JavaException extends Exception {

    private static final long serialVersionUID = 801342581753517148L;

    public JavaException(){}

    public JavaException(String message){
        super(message);
    }

    public JavaException(String message, Throwable throwable){
        super(message,throwable);
    }

    public JavaException(Throwable throwable){
        super(throwable);
    }
}
