package com.fu.demo.base.strategy;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 前端选择策略执行
 */
@RestController
@RequestMapping("strategy")
public class StrategyController {
    @Resource
    private StrategyContext strategyContext;

    @GetMapping("{type}")
    public boolean strategy(@PathVariable String type){
        //这里因为懒。。就没有加上Service层了，直接在控制器处理
        return strategyContext.strategyHandle(type);
    }
}
