package com.fu.springbootdemo;

import com.fu.springbootdemo.util.ApplicationContextUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ApplicationContextUtilTests {
    static ApplicationContext ac1 = null;
    static ApplicationContext ac2 = null;

    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ac1 = ApplicationContextUtil.getApplicationContext();
        });
        t1.start();Thread t2 = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ac2 = ApplicationContextUtil.getApplicationContext();
        });
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ac1);
        System.out.println(ac2);
        System.out.println(ac1 == ac2);
    }

}
