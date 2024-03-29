package com.fu.springbootdemo.util;

import com.fu.springbootdemo.global.Code;
import com.fu.springbootdemo.global.Err;
import com.fu.springbootdemo.global.GlobalVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 当前登录用户工具类
 */
public class CurrentLoginUserUtil {
    private static final Logger log = LoggerFactory.getLogger(CurrentLoginUserUtil.class);
    private CurrentLoginUserUtil(){}

    /**
     * 根据Token获取当前登录用户的ID。
     * 警告：该方法必须经过认证才能使用！即调用该方法的请求头必须包含token
     */
    public static int getUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //Redis的Token key 就是UUID + 用户ID，删除32位长度的UUID以后就是用户ID。
        String redisTokenKey = request.getHeader(GlobalVariable.TOKEN);
        if (!StringUtils.hasText(redisTokenKey)){
            log.error("errCode:{},errMessage:{}",Code.NOT_LOGIN.getErrCode(),Code.NOT_LOGIN.getErrMessage());
            throw Err.codeAndMsg(Code.NOT_LOGIN);
        }
        return Integer.parseInt(redisTokenKey.substring(32));
    }

}
