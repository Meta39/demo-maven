package com.fu.springbootdemo.controller;

import com.fu.springbootdemo.entity.User;
import com.fu.springbootdemo.global.Code;
import com.fu.springbootdemo.global.Err;
import com.fu.springbootdemo.global.TokenInfo;
import com.fu.springbootdemo.service.RoleService;
import com.fu.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.*;

import static com.fu.springbootdemo.global.GlobalVariable.TOKEN;

@RestController
public class LoginController {
    @Value("${fu.authentication.token-timeout}")
    private int tokenTimeout;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("login")
    public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = this.userService.selectUserByUsername(username);
        if (user == null) {
            throw Err.setMessage("登录用户名不存在");
        }
        // TODO 密码加盐salt加/解密工具类
        if (!Objects.equals(password, user.getPwd())) {
            throw Err.setMessage("密码错误");
        }
        // TODO 验证码
        TokenInfo tokenInfo = TokenInfo.getTokenInfo();
        tokenInfo.setUserId(user.getId());
        Set<Integer> roleIds = this.userService.selectUserRoleIds(user.getId());
        tokenInfo.setRoleIds(roleIds);
        tokenInfo.setAuthorizes(this.userService.selectUserAuthorizes(roleIds));
        Map<String, Object> map = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        //把登录用户信息存入Redis
        this.redisTemplate.opsForValue().set(TOKEN + ":" + uuid, tokenInfo, Duration.ofSeconds(tokenTimeout));
        map.put("token", uuid);
        map.put("username", username);
        Set<String> roleNames = roleIds.isEmpty() ? null : this.roleService.selectRoleNamesByIds(roleIds);
        map.put("roleNames", roleNames);
        return map;
    }

    /**
     * 登出
     */
    @PostMapping("logout")
    public Boolean logout(HttpServletRequest request) {
        String token = TOKEN + ":" + request.getHeader(TOKEN);
        if (Boolean.TRUE.equals(this.redisTemplate.hasKey(token))) {
            return this.redisTemplate.delete(token);
        }
        throw Err.setCodeAndMessage(Code.NOT_LOGIN.getErrCode(), Code.NOT_LOGIN.getErrMessage());
    }
}
