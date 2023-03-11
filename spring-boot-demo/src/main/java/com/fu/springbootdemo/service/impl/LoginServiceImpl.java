package com.fu.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fu.springbootdemo.entity.Role;
import com.fu.springbootdemo.entity.User;
import com.fu.springbootdemo.global.Code;
import com.fu.springbootdemo.global.Err;
import com.fu.springbootdemo.global.GlobalAuthenticationFilter;
import com.fu.springbootdemo.global.TokenInfo;
import com.fu.springbootdemo.mapper.LoginMapper;
import com.fu.springbootdemo.mapper.RoleMapper;
import com.fu.springbootdemo.mapper.UserMapper;
import com.fu.springbootdemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.fu.springbootdemo.global.GlobalVariable.TOKEN;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private GlobalAuthenticationFilter globalAuthenticationFilter;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public Map<String, Object> login(String username, String password) {
        User user = this.userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                //没有禁用的
                .eq(User::getIsBan, 0));
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
        Set<Integer> roleIds = this.loginMapper.selectUserRoleInfo(user.getId());
        tokenInfo.setRoleIds(roleIds);
        tokenInfo.setAuthorizes(this.loginMapper.selectUserAuthorizes(roleIds));
        Map<String, Object> map = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        //把登录用户信息存入Redis
        this.redisTemplate.opsForValue().set(TOKEN + ":" + uuid, tokenInfo, Duration.ofSeconds(globalAuthenticationFilter.getTokenTimeout()));
        map.put("token", uuid);
        //返回token过期时间给前端，让前端判断token快过期时，调用续期接口，实现无感刷新。
        map.put("tokenTimeout",globalAuthenticationFilter.getTokenTimeout());
        map.put("username", username);
        System.out.println(roleIds.isEmpty());
        Set<String> roleNames = roleIds.isEmpty() ? null : this.roleMapper.selectBatchIds(roleIds).stream().map(Role::getRoleName).collect(Collectors.toSet());
        map.put("roleNames", roleNames);
        return map;
    }

    /**
     * 登出
     */
    @Override
    public Boolean logout(HttpServletRequest request) {
        String token = TOKEN + ":" + request.getHeader(TOKEN);
        if (Boolean.TRUE.equals(this.redisTemplate.hasKey(token))) {
            return this.redisTemplate.delete(token);
        }
        throw Err.setCodeAndMessage(Code.NOT_LOGIN.getErrCode(), Code.NOT_LOGIN.getErrMessage());
    }

    /**
     * 续期token
     */
    @Override
    public Boolean token(HttpServletRequest request) {
        String token = TOKEN + ":" + request.getHeader(TOKEN);
        if (Boolean.TRUE.equals(this.redisTemplate.hasKey(token))) {
            return this.redisTemplate.expire(token,Duration.ofSeconds(globalAuthenticationFilter.getTokenTimeout()));
        }
        throw Err.setCodeAndMessage(Code.NOT_LOGIN.getErrCode(), Code.NOT_LOGIN.getErrMessage());
    }

}
