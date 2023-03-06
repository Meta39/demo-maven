package com.fu.demo.filter;

import com.fu.demo.config.NotCheckConfig;
import com.fu.demo.entity.Status;
import com.fu.demo.entity.Err;
import com.fu.demo.entity.TokenInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Filter拦截器，token拦截，需要使用的时候开启@Component和@WebFilter注解
 */
//@Component
//@WebFilter(urlPatterns = "/*", filterName = "tokenFilter")
public class TokenFilter implements Filter {
    @Value("${token-overtime}")
    private int tokenOvertime;
    //yml list形式过滤
    @Resource
    private NotCheckConfig notCheckConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("token");//请求头的token
        String requestURI = request.getRequestURI();//请求地址
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean pass = notCheckConfig.getNotCheck().stream().anyMatch(patternUri -> antPathMatcher.match(patternUri,requestURI));//是否包含白名单uri
        System.out.println(requestURI +"："+ pass);
        if (pass) {
            filterChain.doFilter(request, response);//通过认证
        } else if (StringUtils.hasLength(token) && Boolean.TRUE.equals(redisTemplate.hasKey(token))) {//token认证
            //授权
            TokenInfo tokenInfo = (TokenInfo) redisTemplate.opsForValue().get(token);
            assert tokenInfo != null;
            boolean empower = tokenInfo.getEmpowers() != null && tokenInfo.getEmpowers().size() > 0 && tokenInfo.getEmpowers().stream().anyMatch((requestURI)::equals);
            if (empower){//认证并授权则通过
                redisTemplate.expire(token,tokenOvertime,TimeUnit.SECONDS);//刷新token过期时间
                filterChain.doFilter(request, response);//通过认证和授权
            }else {//认证但未授权，抛出异常
                resolver.resolveException(request, response, null,new Err(Status.ILLEGAL_REQUEST.getStatus(),Status.ILLEGAL_REQUEST.getMsg()));
            }
        } else {//未认证的非法请求拦截，抛出异常
            //抛出异常
            resolver.resolveException(request, response, null, !StringUtils.hasLength(token) ? new Err("请求头token不能为空！") : new Err(Status.NOT_LOGIN.getStatus(), Status.NOT_LOGIN.getMsg()));
        }
    }
}

