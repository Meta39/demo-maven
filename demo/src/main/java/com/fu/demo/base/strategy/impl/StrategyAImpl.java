package com.fu.demo.base.strategy.impl;

import com.fu.demo.base.strategy.IStrategy;
import org.springframework.stereotype.Component;

@Component("StrategyAImpl")//这里的bean name要和枚举类里的className一致
public class StrategyAImpl implements IStrategy {

    @Override
    public boolean execute() {
        //A策略逻辑
        System.out.println("执行A策略......");
        return true;
    }
}
