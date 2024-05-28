package com.fu.springbootreadwritesplittingdemo.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 创建日期：2024-05-28
 */
public class DSRouting extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DSContextHolder.get();
    }
}

