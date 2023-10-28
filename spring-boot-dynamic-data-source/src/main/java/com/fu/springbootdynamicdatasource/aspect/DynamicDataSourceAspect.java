package com.fu.springbootdynamicdatasource.aspect;

import com.fu.springbootdynamicdatasource.annotation.ChangeDataSource;
import com.fu.springbootdynamicdatasource.datasource.DataSourceContextHolder;
import com.fu.springbootdynamicdatasource.enums.DataSources;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 动态数据源切面
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    /**
     * 执行方法前
     * @param changeDataSource 切换数据源注解
     */
    @Before("@annotation(changeDataSource)")
    public void doBefore(ChangeDataSource changeDataSource) {
        DataSources dataSources = changeDataSource.value();//获取切换的数据源
        DataSourceContextHolder.setBranchContext(dataSources);//设置切换后的数据源
    }

    /**
     * 环绕销毁线程变量，这样就可以不用在@After和@@AfterThrowing再写一次DataSourceContextHolder.clearBranchContext()。
     * @param point 切点
     * @param changeDataSource 切换数据源注解
     */
    @Around("@annotation(changeDataSource)")
    public Object doAround(ProceedingJoinPoint point, ChangeDataSource changeDataSource) throws Throwable {
        DataSourceContextHolder.clearBranchContext();//销毁线程变量，防止内存溢出。
        return point.proceed();
    }

}