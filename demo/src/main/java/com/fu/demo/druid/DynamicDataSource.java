package com.fu.demo.druid;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        this.setDefaultTargetDataSource(defaultTargetDataSource);
        this.setTargetDataSources(targetDataSources);
        this.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceAspect.DATA_SOURCE_LIST.get((int) redisTemplate.opsForValue().get(DataSourceAspect.INDEX));
    }
}
