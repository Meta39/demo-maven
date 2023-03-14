package com.fu.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fu.springbootdemo.entity.Authorize;
import com.fu.springbootdemo.entity.Role;
import com.fu.springbootdemo.entity.RoleAuthorize;
import com.fu.springbootdemo.entity.User;
import com.fu.springbootdemo.global.*;
import com.fu.springbootdemo.mapper.*;
import com.fu.springbootdemo.service.LoginService;
import com.fu.springbootdemo.util.DataBasePasswordUtil;
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
    private RoleAuthorizeMapper roleAuthorizeMapper;
    @Autowired
    private AuthorizeMapper authorizeMapper;
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
    public TokenFrontInfo login(String passwordPublicKeyUUID,String username, String password) {
        User user = this.userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        if (user == null) {
            throw Err.setMessage("登录用户名不存在");
        }
        if (user.getIsBan() == 1){
            throw Err.setMessage("登录用户被禁用");
        }
        //前端加盐加密密码密文解密后拿到原始密码，拿到数据库的密码和盐对数据库的密码进行解密后匹配两个密码是否相等。
        if (!Objects.equals(this.passwordUtil.decrypt(passwordPublicKeyUUID,password), DataBasePasswordUtil.decrypt(user.getPwd(),user.getSalt()))) {
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
        //判断是否包含超级管理员角色
        if (roleIds.stream().anyMatch(roleId -> roleId == 1)){
            tokenInfo.setAuthorizes(this.authorizeMapper.selectList(null).stream().map(Authorize::getAuthorizeName).collect(Collectors.toSet()));
        }else {
            tokenInfo.setAuthorizes(this.loginMapper.selectUserAuthorizes(roleIds));
        }
        String token = UUID.randomUUID().toString();
        //把登录用户信息存入Redis
        this.redisTemplate.opsForValue().set(TOKEN + ":" + token, tokenInfo, Duration.ofSeconds(globalAuthenticationFilter.getTokenTimeout()));
        TokenFrontInfo tokenFrontInfo = TokenFrontInfo.getInstance();
        tokenFrontInfo.setToken(token);
        //返回token过期时间给前端，让前端判断token快过期时，调用续期接口，实现无感刷新。
        tokenFrontInfo.setTokenTimeout(globalAuthenticationFilter.getTokenTimeout());
        tokenFrontInfo.setUsername(username);
        List<Role> roles = roleIds.isEmpty() ? null : this.roleMapper.selectBatchIds(roleIds);
        tokenFrontInfo.setRoles(roles);
        List<Authorize> authorizes = new ArrayList<>();
        if (roles != null && !roles.isEmpty()){
            //超级管理员直接获取全部权限集合
            if (roles.stream().anyMatch(role -> role.getId() == 1)){
                authorizes = this.authorizeMapper.selectList(null);
            }else {
                for (Role role : roles) {//根据角色ID获取角色权限ID集合
                    List<RoleAuthorize> roleAuthorizes = this.roleAuthorizeMapper.selectList(new LambdaQueryWrapper<RoleAuthorize>().eq(RoleAuthorize::getRoleId, role.getId()));
                    for (RoleAuthorize roleAuthorize : roleAuthorizes) {//获取其中一个角色的所有权限列表
                        List<Authorize> oneRoleAuthorizes = this.authorizeMapper.selectList(new LambdaQueryWrapper<Authorize>().eq(Authorize::getId, roleAuthorize.getAuthorizeId()));
                        authorizes.addAll(oneRoleAuthorizes);
                    }
                }
            }
            //递归权限树
            List<Authorize> finalAuthorizes = authorizes;
            authorizes = authorizes.stream().filter(authorize -> authorize.getPId() == 0).peek(authorize -> authorize.setChildAuthorize(authorizesTree(authorize, finalAuthorizes))).collect(Collectors.toList());
        }
        tokenFrontInfo.setAuthorizes(authorizes);
        return tokenFrontInfo;
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

    //-----------------------------------------内部方法------------------------------------------------------

    /**
     * 递归权限树
     */
    private static List<Authorize> authorizesTree(Authorize authorize,List<Authorize> authorizes){
        return authorizes.stream()
                .filter(a -> a.getPId().equals(authorize.getId()))
                .peek(a -> a.setChildAuthorize(authorizesTree(a,authorizes)))
                .collect(Collectors.toList());
    }

}
