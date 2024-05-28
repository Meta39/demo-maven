package com.fu.springbootreadwritesplittingdemo;

import com.fu.springbootreadwritesplittingdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 创建日期：2024-05-28
 */
@SpringBootTest
public class SpringBootReadwriteSplittingDemoTests {
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        System.out.println(userService.selectById(1L));
    }

}
