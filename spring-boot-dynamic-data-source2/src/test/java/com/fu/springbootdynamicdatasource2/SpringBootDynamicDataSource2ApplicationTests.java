package com.fu.springbootdynamicdatasource2;

import com.fu.springbootdynamicdatasource2.mapper.mysql1.MySQL1UserMapper;
import com.fu.springbootdynamicdatasource2.mapper.mysql2.MySQL2UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class SpringBootDynamicDataSource2ApplicationTests {
    @Autowired
    private MySQL1UserMapper mySQL1UserMapper;
    @Autowired
    private MySQL2UserMapper mySQL2UserMapper;

    @Test
    void test() {
        log.info("数据源1用户数据：{}", mySQL1UserMapper.selectById(1));//数据源1
        log.info("数据源2用户数据{}", mySQL2UserMapper.selectById(1));//数据源2
    }
}