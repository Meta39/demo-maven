package com.fu.springbootdemo.service;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {

    /**
     * 登录接口
     */
    Map<String, Object> login(String username,String password);

    /**
     * 登出接口
     */
    Boolean logout(HttpServletRequest request);

}
