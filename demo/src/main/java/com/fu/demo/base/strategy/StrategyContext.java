package com.fu.demo.base.strategy;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;

@Component
public class StrategyContext {
    @Resource
    private StrategyFactory strategyFactory;

    public boolean strategyHandle(String type){
        //将某属性的值转换成具体的枚举。这里是根据type对应枚举的code进行转换
        Optional<StrategyEnum> payStrategyEnumOptional = Arrays.stream(StrategyEnum.class.getEnumConstants())
                .filter((e) -> e.getCode().equals(type)).findAny();

        if(!payStrategyEnumOptional.isPresent()){
            System.out.println("匹配不到策略！");
            return false;
        }
        StrategyEnum payStrategyEnum = payStrategyEnumOptional.get();
        IStrategy payStrategy = strategyFactory.getPayStrategy(payStrategyEnum);
        //进行参数的处理.....
        boolean result = payStrategy.execute();

        return true;

    }
}
