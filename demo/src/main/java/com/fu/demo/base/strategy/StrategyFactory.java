package com.fu.demo.base.strategy;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class StrategyFactory {

    @Resource
    private Map<String, IStrategy> strategyMap;
    
    public IStrategy getPayStrategy(StrategyEnum strategyEnum){
        if(!strategyMap.containsKey(strategyEnum.getClassName())){
            System.out.println("没有对应的策略！");
            return null;
        }
        return strategyMap.get(strategyEnum.getClassName());
    }
}