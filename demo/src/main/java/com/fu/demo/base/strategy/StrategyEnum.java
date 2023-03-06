package com.fu.demo.base.strategy;

public enum StrategyEnum {
    STRATEGY_A("1","StrategyAImpl"),
    STRATEGY_B("2","StrategyBImpl");

    private final String code;

    /**
     * bean名称
     */
    private final String className;

    StrategyEnum(String code, String className) {
        this.code = code;
        this.className = className;
    }

    public String getCode() {
        return code;
    }

    public String getClassName() {
        return className;
    }
}
