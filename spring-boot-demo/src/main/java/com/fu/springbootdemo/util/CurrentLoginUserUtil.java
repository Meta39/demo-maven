package com.fu.springbootdemo.util;

import com.fu.springbootdemo.global.Code;
import com.fu.springbootdemo.global.Err;
import com.fu.springbootdemo.global.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.fu.springbootdemo.global.GlobalVariable.TOKEN;

/**
 * 当前登录用户工具类
 */
@Component
public class CurrentLoginUserUtil {

    private static RedisTemplate<String,Object> redisTemplate;


    @Autowired
    public void setRedisTemplate(RedisTemplate<String,Object> redisTemplate){
        CurrentLoginUserUtil.redisTemplate = redisTemplate;
    }

    /**
     * 获取当前登录用户的ID
     */
    public static Integer getUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String redisTokenKey = TOKEN + ":" + request.getHeader(TOKEN);
        Boolean hasLogin = redisTemplate.hasKey(redisTokenKey);
        //判断是否有token，没有token则不执行
        if (Boolean.TRUE.equals(hasLogin)){
            TokenInfo tokenInfo = (TokenInfo) redisTemplate.opsForValue().get(redisTokenKey);
            //判断tokenInfo是否为空以及tokenInfo.getUserId()是否有值
            if (tokenInfo == null || tokenInfo.getUserId() == null){
                throw Err.setCodeAndMessage(Code.NOT_LOGIN.getErrCode(),Code.NOT_LOGIN.getErrMessage());
            }
            return tokenInfo.getUserId();
        }
        throw Err.setCodeAndMessage(Code.NOT_LOGIN.getErrCode(),Code.NOT_LOGIN.getErrMessage());
    }

}
