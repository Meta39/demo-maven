package com.fu.springbootdemo.service;


import com.fu.springbootdemo.global.TokenFrontInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {
    /**
     * 根据前端传的UUID返回公钥和盐给前端对密码进行加密
     */
    Map<String,Object> getEncryptPublicKeyAndSalt(String UUID);

    /**
     * 登录接口
     */
    TokenFrontInfo login(String passwordPublicKeyUUID, String username, String password);

    /**
     * 登出接口
     */
    Boolean logout(HttpServletRequest request);

    /**
     * 续期token
     */
    Boolean token(HttpServletRequest request);

}
