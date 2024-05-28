package com.fu.springbootreadwritesplittingdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建日期：2024-05-28
 */
@Slf4j
@Aspect
@Component
public class DSAspect {
    protected static final AtomicInteger DB_INDEX = new AtomicInteger(0);

    @Pointcut("@annotation(com.fu.springbootreadwritesplittingdemo.config.DS)")
    public void dsPointCut() {

    }

    @Around("dsPointCut() && @annotation(ds)")
    public Object around(ProceedingJoinPoint joinPoint, DS ds) throws Throwable {
        DB nowDb = roundRobinDB();
        DSContextHolder.set(nowDb);
        log.info("当前数据源为：{}", nowDb);
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DSContextHolder.clear();
        }
    }

    /**
     * 轮询
     */
    protected DB roundRobinDB() {
        int index = DB_INDEX.getAndIncrement();
        if (index >= DB.VALUES_LENGTH - 1) {
            DB_INDEX.set(0);
            return DB.getByIndex(0);
        }
        ++index;
        DB_INDEX.set(index);
        return DB.getByIndex(index);
    }

}

