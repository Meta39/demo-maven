package com.fu.springbootdemo.controller;

import com.fu.springbootdemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录相关接口
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("login")
    public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return this.loginService.login(username,password);
    }

    /**
     * 登出
     */
    @PostMapping("logout")
    public Boolean logout(HttpServletRequest request) {
        return this.loginService.logout(request);
    }

    /**
     * 续期token
     */
    @PostMapping("refreshToken")
    public Boolean token(HttpServletRequest request){
        return this.loginService.token(request);
    }

}
