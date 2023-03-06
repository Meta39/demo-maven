package com.fu.demo.controller;

import com.fu.demo.service.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AsyncController {
    @Resource
    private AsyncService asyncService;

    /**
     * 本质上，任务花费的时间越长，同时调用的任务越多，您从异步处理中看到的好处就越大。权衡是处理CompletableFuture接口。它增加了一层间接性，因为您不再直接处理结果。
     */
    @GetMapping("user/selectByAsync")
    public void selectByAsync(){
        asyncService.selectByAsync();
    }
}
