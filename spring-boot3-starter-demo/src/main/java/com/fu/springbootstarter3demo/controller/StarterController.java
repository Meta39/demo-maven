package com.fu.springbootstarter3demo.controller;

import com.fu.springbootstarter3demo.properties.StarterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StarterController {
    @Autowired
    private StarterProperties starterProperties;

    @GetMapping("hello2")
    public String hello() {
        return "您好，我叫【" + this.starterProperties.getName() + "】，今年【" + this.starterProperties.getAge() + "】岁";
    }

}
