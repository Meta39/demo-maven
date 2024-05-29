package com.fu.springbootreadwritesplittingdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建日期：2024-05-28
 */
@Slf4j
@Aspect
@Component
public class ReadOnlyAspect {
    protected static final AtomicInteger DB_INDEX = new AtomicInteger(0);

    @Pointcut("@annotation(com.fu.springbootreadwritesplittingdemo.config.ReadOnly)")
    public void dsPointCut() {

    }

    /**
     * 不能同时使用 @Transactional 和 @ReadOnly 注解
     * @param transactional 事务注解
     * @param readOnly 只读注解
     */
    @Before(value = "@annotation(transactional) && @annotation(readOnly)", argNames = "transactional,readOnly")
    public void before(Transactional transactional, ReadOnly readOnly) {
        if (transactional != null && readOnly != null) {
            throw new RuntimeException("Using " + Transactional.class.getSimpleName() + " and " + ReadOnly.class.getSimpleName() + " together is not allowed");
        }
    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        SlaveDB nowDb = roundRobinDB();
        ReadOnlyContextHolder.set(nowDb);
        log.info("当前只读数据源为：{}", nowDb);
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ReadOnlyContextHolder.clear();
        }
    }

    /**
     * 轮询
     */
    protected SlaveDB roundRobinDB() {
        int index = DB_INDEX.getAndIncrement();
        if (index >= SlaveDB.VALUES_LENGTH - 1) {
            DB_INDEX.set(0);
            return SlaveDB.getByIndex(0);
        }
        ++index;
        DB_INDEX.set(index);
        return SlaveDB.getByIndex(index);
    }

}

