package com.fu.springbootdynamicdatasource.dynamicdatasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mysql1")
public class DataSourceMySQL1Properties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String type;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private boolean testOnBorrow;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;
}