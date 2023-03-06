package com.fu.demo.aop.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.demo.aop.LogAnnotate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志切面
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(com.fu.demo.aop.LogAnnotate)")
    public void logOperation() {}

    /**
     * 切点之前执行
     */
    @Before("logOperation()")
    public void doBefore(JoinPoint point) throws JsonProcessingException {
        //开始打印日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取自定义注解的信息
        log.info("====================================================");
        log.info("URL：{}",request.getRequestURL().toString());
        log.info("HTTP Method：{}",request.getMethod());
        log.info("Class Method：{}.{}",point.getSignature().getDeclaringTypeName(),point.getSignature().getName());
        log.info("IP：{}",request.getRemoteAddr());
        log.info("Request Args：{}",new ObjectMapper().writeValueAsString(point.getArgs()));
    }

    /**
     * 切点之后执行
     */
    @After("logOperation()")
    public void  doAfter(){
        log.info("====================================================");
    }

    /**
     * 抛出异常时执行
     */
    @AfterThrowing("logOperation()")
    public void afterThrowing(JoinPoint point){
        log.info("切点异常");
    }

    /**
     * 环绕是最后执行的
     */
    @Around("logOperation()&&@annotation(logAnnotate)")
    public Object doAround(ProceedingJoinPoint point, LogAnnotate logAnnotate) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Object result = point.proceed();//执行切点
        log.info("Response Args：{}",new ObjectMapper().writeValueAsString(result));//打印参数
        log.info("LogAOP param value:{}", logAnnotate.value());
        return result;
    }
}

