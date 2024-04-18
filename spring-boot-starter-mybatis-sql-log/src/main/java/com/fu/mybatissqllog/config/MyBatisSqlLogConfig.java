package com.fu.mybatissqllog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mybatis-log")
public class MyBatisSqlLogConfig {
    private Boolean enableLog = false;

    public Boolean getEnableLog() {
        return enableLog;
    }

    public void setEnableLog(Boolean enableLog) {
        this.enableLog = enableLog;
    }
}
