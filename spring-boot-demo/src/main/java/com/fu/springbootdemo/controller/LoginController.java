package com.fu.springbootdemo.controller;

import com.fu.springbootdemo.global.LoginDTO;
import com.fu.springbootdemo.global.TokenInfo;
import com.fu.springbootdemo.global.UpdatePwdDTO;
import com.fu.springbootdemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
     * @param loginDTO 登录DTO
     */
    @PostMapping("login")
    public TokenInfo login(@RequestBody @Valid LoginDTO loginDTO) {
        return this.loginService.login(loginDTO);
    }

    /**
     * 登出
     */
    @PostMapping("logout")
    public Boolean logout(HttpServletRequest request) {
        return this.loginService.logout(request);
    }

    /**
     * 修改当前登录用户密码
     */
    @PostMapping("updatePwd")
    public Boolean updatePwd(@RequestBody UpdatePwdDTO updatePwdDTO){
        return this.loginService.updatePwd(updatePwdDTO);
    }

    /**
     * 续期token：前端判断token是否快到期，快到期就调这个接口续期。
     */
    @PostMapping("refreshToken")
    public Boolean token(HttpServletRequest request){
        return this.loginService.token(request);
    }

}
