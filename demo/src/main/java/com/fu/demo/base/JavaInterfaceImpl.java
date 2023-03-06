package com.fu.demo.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 实现接口
 * 没有default关键字必须重写接口方法，java8+如果接口有default关键字则可以不重写方法
 */
@Slf4j
public class JavaInterfaceImpl implements JavaInterface{

    /**
     * 实现接口的方法
     */
    @Test
    @Override
    public void needToImpl() {
        log.info("实现方法");
    }

}
