package com.fu.springbootdynamicdatasource.datasource;

import com.fu.springbootdynamicdatasource.enums.DataSources;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    /**
     * 默认数据源
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql1")
    public DataSource defaultTargetDataSource() {
        return new HikariDataSource();
    }

    /**
     * 默认数据源对应的事务
     * @param dataSource 默认数据源
     */
    @Bean
    @Primary
    public DataSourceTransactionManager defaultTransactionManager(@Qualifier("defaultTargetDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 第二个数据源
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql2")
    public DataSource secondDataSource() {
        return new HikariDataSource();
    }

    /**
     * 第二个数据源对应的事务
     * @param dataSource 第二个数据源
     */
    @Bean
    public DataSourceTransactionManager secondTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 数据源初始化
     */
    @Bean
    @Primary
    public DataSourceRouting dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSources.ONE, defaultTargetDataSource());
        targetDataSources.put(DataSources.TWO, secondDataSource());
        return new DataSourceRouting(defaultTargetDataSource(), targetDataSources);
    }
}