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
import com.fu.springbootdemo.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.fu.springbootdemo.global.GlobalVariable.*;

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
    @Autowired
    private PasswordUtil passwordUtil;

    /**
     * 根据前端传的UUID返回公钥和盐给前端对密码进行加密
     */
    @Override
    public Map<String, Object> getEncryptPublicKeyAndSalt(String UUID) {
        this.passwordUtil.generatorKeyPair(UUID);
        Map<String, String> map = this.passwordUtil.checkUUID(UUID);//校验
        Map<String,Object> resultMap = new HashMap<>(8);
        resultMap.put(RSA_PUBLIC_KEY,map.get(RSA_PUBLIC_KEY)); //公钥
        resultMap.put(HEAD_SALT,map.get(HEAD_SALT)); //头部盐
        resultMap.put(TAIL_SALT,map.get(TAIL_SALT)); //尾部盐
        return resultMap;
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public Map<String, Object> login(String passwordPublicKeyUUID,String username, String password) {
        User user = this.userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                //没有禁用的
                .eq(User::getIsBan, 0));
        if (user == null) {
            throw Err.setMessage("登录用户名不存在");
        }
        // TODO 数据库密码加密
        if (!Objects.equals(this.passwordUtil.decrypt(passwordPublicKeyUUID,password), user.getPwd())) {
            throw Err.setMessage("密码错误");
        }
        //校验密码成功后要把redis里前端传递过来的UUID删除。
        String redisRsaKey = ENCRYPT_TYPE + ":" + passwordPublicKeyUUID;
        Boolean hasKey = this.redisTemplate.hasKey(redisRsaKey);
        if (hasKey != null && hasKey){
            this.redisTemplate.delete(redisRsaKey);
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
