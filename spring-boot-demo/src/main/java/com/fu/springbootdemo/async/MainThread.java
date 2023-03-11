package com.fu.springbootdemo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 主线程
 */
@Slf4j
@Service
public class MainThread {
    @Autowired
    private AsyncThread asyncThread;

    public String mainThread(){
        log.info("主线程开始执行...");
        asyncThread.asyncThread();
        log.info("主线程执行结束...");
        return "ok";
    }

}
