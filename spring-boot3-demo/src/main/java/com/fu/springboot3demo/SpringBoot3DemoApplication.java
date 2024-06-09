package com.fu.springboot3demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching //启用缓存并使用 caffeine
@MapperScan("com.fu.springboot3demo.mapper")
@SpringBootApplication
public class SpringBoot3DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot3DemoApplication.class, args);
    }

}