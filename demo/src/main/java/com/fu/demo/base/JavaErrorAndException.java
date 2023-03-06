package com.fu.demo.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Error、Exception
 * Error 用来表示 JVM 无法处理的错误，与代码编写者所执行的操作无关，正常情况下，不大可能出现的情况。如：OutOfMemoryError内存溢出
 * Exception：1、受检异常 ：需要用 try...catch... 语句捕获并进行处理，并且可以从异常中恢复；即上一个类有throw抛出异常，使用此类时也要抛出或try捕获异常。
 *           2、非受检异常 ：是程序运行时错误，例如除 0 会引发 Arithmetic Exception，此时程序崩溃并且无法恢复。如：NullPointException
 */
@Slf4j
public class JavaErrorAndException {

    /**
     * 非运行时异常，编译不通过，并且需要在使用的方法() throws Exception和方法里面throw new Exception()
     * @throws JavaException
     */
    @Test
    public void throwJavaException() throws JavaException{
        throw new JavaException("JavaException继承Exception非运行时异常，编译不通过");
    }

    /**
     * 运行时异常，编译通过，在需要的使用的方法内throw new JavaRuntimeException();
     */
    public void throwJavaRuntimeException(){
        //一般结合加上了@RestControllerAdvice注解的类使用@ExceptionHandler(value =JavaRuntimeException.class)
        JavaRuntimeException javaRuntimeException = new JavaRuntimeException();
        javaRuntimeException.setName("异常名称");
        javaRuntimeException.setMsg("异常信息");
        throw javaRuntimeException;
    }

}
