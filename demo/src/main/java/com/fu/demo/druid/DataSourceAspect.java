package com.fu.demo.druid;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    public static final String INDEX = "index";
    public static final List<String> DATA_SOURCE_LIST = new ArrayList<String>(2) {{
        this.add("mysql1");
        this.add("mysql2");
    }};

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Around("execution(public * com.fu.demo.dao.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            robin();//轮询数据库
            return point.proceed();
        } catch (MyBatisSystemException e) {
            robin();//连接不上就再轮询一次，获取另外一个mysql数据库连接
            return point.proceed();
        }
    }

    /**
     * 轮询mysql数据库
     */
    public void robin() {
        //没有key就创建key
        if (!redisTemplate.hasKey(INDEX)) {
            redisTemplate.opsForValue().set(INDEX, 0);
        } else {
            //有key就直接获取
            int getIndex = (int) redisTemplate.opsForValue().get(INDEX);
            //超过list集合的长度减一就重新赋值（轮询），利用redis单线程的特性存放全局index下标
            if (getIndex >= DATA_SOURCE_LIST.size() - 1) {
                redisTemplate.opsForValue().set(INDEX, 0);
            } else {
                redisTemplate.opsForValue().set(INDEX, ++getIndex);
            }
        }
    }
}
