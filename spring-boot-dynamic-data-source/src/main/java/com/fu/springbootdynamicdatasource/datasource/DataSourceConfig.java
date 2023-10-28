package com.fu.springbootdynamicdatasource.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
//@EnableTransactionManagement
@RequiredArgsConstructor
@DependsOn("dataSourceRouting")
public class DataSourceConfig {
    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSource dataSource() {
        return dataSourceRouting;
    }
}