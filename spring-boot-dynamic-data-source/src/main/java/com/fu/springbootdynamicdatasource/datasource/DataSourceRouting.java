package com.fu.springbootdynamicdatasource.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceRouting extends AbstractRoutingDataSource {

    public DataSourceRouting(DataSource defaultTargetDataSource, Map<Object, Object> dataSourceMap) {
        this.setDefaultTargetDataSource(defaultTargetDataSource);
        this.setTargetDataSources(dataSourceMap);
    }

    /**
     * 获取线程变量里设置的数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }

}