package com.fu.springbootdynamicdatasource.annotation;

import com.fu.springbootdynamicdatasource.enums.DataSources;

import java.lang.annotation.*;

/**
 * 切换数据源
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeDataSource {
    DataSources value() default DataSources.ONE;
}
