package com.fu.springboot3demo.one;

import com.fu.springboot3demo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MySpringBootConfig {

    @Scope("prototype")//多实例
    @Bean("user")
    public User user(){
        User user = new User();
        user.setId(1L);
        user.setName("Meta39");
        return user;
    }

}
