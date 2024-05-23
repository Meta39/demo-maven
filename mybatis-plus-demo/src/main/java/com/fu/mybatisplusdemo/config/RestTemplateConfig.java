package com.fu.mybatisplusdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        //一般情况下会使用http连接池，这里只是其他作用，就不使用连接池了
        return new RestTemplate();
    }

}
