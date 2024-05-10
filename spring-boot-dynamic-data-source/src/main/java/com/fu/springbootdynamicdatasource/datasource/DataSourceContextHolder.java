package com.fu.springbootdynamicdatasource.datasource;

import com.fu.springbootdynamicdatasource.enums.DataSources;

/**
 * 数据源线程变量
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<DataSources> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源
     * @param dataSourcesEnum 数据源枚举类
     */
    public static void setDataSource(DataSources dataSourcesEnum) {
        contextHolder.set(dataSourcesEnum);
    }

    /**
     * 获取数据源
     */
    public static DataSources getDataSource() {
        return contextHolder.get();
    }

    /**
     * 销毁数据源
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }

}