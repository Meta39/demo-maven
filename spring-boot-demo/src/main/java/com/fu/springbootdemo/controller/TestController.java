package com.fu.springbootdemo.controller;

import com.fu.springbootdemo.annotation.PreAuthorize;
import com.fu.springbootdemo.annotation.ReturnMeta;
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

    @ReturnMeta //返回原始数据，不用全局返回类封装。
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

    /**
     * 测试权限注解，超级管理员角色跳过该鉴权注解
     */
    @PreAuthorize(authorize = "authorize")
    @GetMapping("authorize")
    public String authorize(){
        return "需要授权才能访问，超级管理员跳过鉴权。";
    }
}
