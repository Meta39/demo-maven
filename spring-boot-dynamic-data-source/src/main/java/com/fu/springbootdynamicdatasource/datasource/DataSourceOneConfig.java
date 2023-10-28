package com.fu.springbootdynamicdatasource.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.datasource.mysql1")
@Getter
@Setter
public class DataSourceOneConfig {
    private String jdbcUrl;
    private String password;
    private String username;
}
