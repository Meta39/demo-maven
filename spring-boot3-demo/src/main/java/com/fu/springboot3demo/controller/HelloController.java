package com.fu.springboot3demo.controller;

import com.fu.springboot3demo.entity.User;
import com.fu.springboot3demo.service.InvokeMethodByBeanNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final InvokeMethodByBeanNameService invokeMethodByBeanNameService;

    @PostMapping("user")
    public User user(@RequestBody User user){
        return user;
    }

    @GetMapping("hello")
    public User hello(){
        return new User();
    }

    @GetMapping("invoke")
    public String invoke(){
        return invokeMethodByBeanNameService.noParamsMethod();
    }

}
