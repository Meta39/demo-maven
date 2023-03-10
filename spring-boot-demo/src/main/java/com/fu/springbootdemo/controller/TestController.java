package com.fu.springbootdemo.controller;

import com.fu.springbootdemo.async.MainThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private MainThread mainThread;

    @GetMapping
    public Integer hello(){
        return 1;
    }

    /**
     * 测试async异步线程
     */
    @GetMapping("async")
    public String async(){
        return this.mainThread.mainThread();
    }

}
