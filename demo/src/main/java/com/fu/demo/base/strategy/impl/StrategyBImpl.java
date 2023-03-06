package com.fu.demo.base.strategy.impl;

import com.fu.demo.base.strategy.IStrategy;
import org.springframework.stereotype.Component;

@Component("StrategyBImpl")//这里的bean name要和枚举类里的className一致
public class StrategyBImpl implements IStrategy {

    @Override
    public boolean execute() {
        //A策略逻辑
        System.out.println("执行B策略......");
        return true;
    }
}
