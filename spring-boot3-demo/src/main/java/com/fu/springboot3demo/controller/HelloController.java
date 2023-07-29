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

    @PostMapping("hello")
    public User hello(@RequestBody User user){
        return user;
    }

    @GetMapping("invoke")
    public String invoke(){
        return invokeMethodByBeanNameService.noParamsMethod();
    }

}
