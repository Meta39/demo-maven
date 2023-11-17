package com.fu.springbootdynamicdatasource;

import com.fu.springbootdynamicdatasource.serviceimpl.ChangeDataSourceServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootDynamicDataSourceApplicationTests {
    @Autowired
    private ChangeDataSourceServiceImpl change;

    @Test
    void test(){
        change.change();
    }
}