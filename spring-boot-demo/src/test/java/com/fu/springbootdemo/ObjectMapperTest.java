package com.fu.springbootdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static com.fu.springbootdemo.global.GlobalVariable.OBJECT_MAPPER;

/**
 * 测试ObjectMapperUtil是否是线程安全的
 */
@Slf4j
public class ObjectMapperTest {
    static ObjectMapper om1 = null;
    static ObjectMapper om2 = null;

    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            om1 = OBJECT_MAPPER;
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            om2 = OBJECT_MAPPER;
        });
        t2.start();
        t1.join();
        t2.join();
        log.info("{}",om1);
        log.info("{}",om2);
        log.info("t1==t2：{}", om1 == om2);
    }
}
