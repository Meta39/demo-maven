package com.fu.springbootdemo.controller;

import com.fu.springbootdemo.service.LoginService;
import com.fu.springbootdemo.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private PasswordUtil passwordUtil;

    /**
     * 跳过认证接口
     * 前端发送login请求之前，再请求这个接口获取公钥和盐，对密码进行加密处理。
     * @param UUID 前端生成的UUID
     */
    @GetMapping("getEncryptPublicKeyAndSalt")
    public Map<String, Object> getEncryptPublicKeyAndSalt(@RequestParam("UUID") String UUID) {
        return this.loginService.getEncryptPublicKeyAndSalt(UUID);
    }

    /**
     * 模拟前端密码加密（dev开发环境下使用。）
     * @param UUID 模拟前端生成的UUID
     * @param noSaltPassword 无盐原始密码
     */
    @GetMapping("encryptPassword")
    public String encryptPassword(@RequestParam("UUID") String UUID,@RequestParam("noSaltPassword") String noSaltPassword){
        //前端传过来的密文会把+号转成空格，因此这里要把空格转换成加号
        return this.passwordUtil.encrypt(UUID,noSaltPassword.replaceAll(" ", "+"));
    }

    /**
     * 登录
     * @param passwordPublicKeyUUID 前端生成的获取公钥的UUID
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("login")
    public Map<String, Object> login(@RequestParam("passwordPublicKeyUUID") String passwordPublicKeyUUID,@RequestParam("username") String username, @RequestParam("password") String password) {
        //前端传过来的密码密文会把+号转成空格，因此这里要把空格转换成加号
        return this.loginService.login(passwordPublicKeyUUID,username,password.replaceAll(" ", "+"));
    }

    /**
     * 登出
     */
    @PostMapping("logout")
    public Boolean logout(HttpServletRequest request) {
        return this.loginService.logout(request);
    }

    /**
     * 续期token：前端判断token是否快到期，快到期就调这个接口续期。
     */
    @PostMapping("refreshToken")
    public Boolean token(HttpServletRequest request){
        return this.loginService.token(request);
    }

}
