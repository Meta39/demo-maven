package com.fu.demo.controller;

import com.fu.demo.aop.LogAnnotate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 例子
 */
@Slf4j
@RestController
public class DemoController {

    @LogAnnotate    //日志切面
    @GetMapping("log")
    public void log(){
        log.info("print the log.");
    }

    @GetMapping("meta")
    public String meta(){
        return "Meta";
    }
}
