package com.fu.springbootdemo.global;

import com.fu.springbootdemo.annotation.PreAuthorize;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.fu.springbootdemo.global.GlobalVariable.TOKEN;

/**
 * 全局鉴权切面
 */
@Aspect
@Component
public class GlobalAuthorizeAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //用自定义注解作为切点
    @Pointcut("@annotation(com.fu.springbootdemo.annotation.PreAuthorize)")
    public void globalAuthorizeAspect() {

    }

    //执行方法前进行鉴权
    @Before("globalAuthorizeAspect()&&@annotation(preAuthorize)")
    public void doBefore(JoinPoint point, PreAuthorize preAuthorize) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String redisTokenKey = TOKEN + ":" + request.getHeader(TOKEN);//redis存放token的key
        String authorize = preAuthorize.authorize();
        TokenInfo tokenInfo = (TokenInfo) this.redisTemplate.opsForValue().get(redisTokenKey);
        if (tokenInfo == null) {
            throw Err.setMessage("登录时没有把TokenInfo放入Redis！");
        }
        if (tokenInfo.getRoleIds() == null || tokenInfo.getRoleIds().isEmpty()) {
            throw Err.setMessage("当前登录用户没有分配角色！");
        }
        boolean admin = tokenInfo.getRoleIds().stream().anyMatch(role -> role == 1);
        //不是超级管理员则进行鉴权，超级管理员角色直接跳过鉴权
        if (!admin && (tokenInfo.getAuthorizes() == null || tokenInfo.getAuthorizes().isEmpty() || tokenInfo.getAuthorizes().stream().noneMatch(a -> Objects.equals(a, authorize)))) {
            throw Err.setCodeAndMessage(Code.NOT_PURVIEW.getErrCode(), Code.NOT_PURVIEW.getErrMessage());
        }
    }

}