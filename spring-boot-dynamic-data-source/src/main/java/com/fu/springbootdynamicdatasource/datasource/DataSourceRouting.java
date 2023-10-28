package com.fu.springbootdynamicdatasource.datasource;

import com.fu.springbootdynamicdatasource.enums.DataSources;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRouting extends AbstractRoutingDataSource {
    private final DataSourceOneConfig dataSourceOneConfig;
    private final DataSourceTwoConfig dataSourceTwoConfig;

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getBranchContext();
    }

    public DataSourceRouting(DataSourceOneConfig dataSourceOneConfig, DataSourceTwoConfig dataSourceTwoConfig) {
        this.dataSourceOneConfig = dataSourceOneConfig;
        this.dataSourceTwoConfig = dataSourceTwoConfig;
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSources.ONE, dataSourceOneDataSource());
        dataSourceMap.put(DataSources.TWO, dataSourceTwoDataSource());
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(dataSourceOneDataSource());
    }


    public DataSource dataSourceOneDataSource() {
        DataSourceProperties dsp = new DataSourceProperties();
        dsp.setUrl(dataSourceOneConfig.getJdbcUrl());
        dsp.setUsername(dataSourceOneConfig.getUsername());
        dsp.setPassword(dataSourceOneConfig.getPassword());
        return dsp.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    public DataSource dataSourceTwoDataSource() {
        DataSourceProperties dsp = new DataSourceProperties();
        dsp.setUrl(dataSourceTwoConfig.getJdbcUrl());
        dsp.setUsername(dataSourceTwoConfig.getUsername());
        dsp.setPassword(dataSourceTwoConfig.getPassword());
        return dsp.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

}