package com.fu.demo.controller;

import com.fu.demo.aop.IgnoreResAnnotate;
import com.fu.demo.dao.LoginDao;
import com.fu.demo.entity.*;
import com.fu.demo.util.RSAUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class LoginController {
    @Value("${token-overtime}")
    private int tokenOvertime;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    LoginDao loginDao;

    @IgnoreResAnnotate //不反回Res
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("getVCode")
    public String getVCode() {
        String vCode = String.format("%04d", new Random().nextInt(999999));//随机验证码
        redisTemplate.opsForValue().set(vCode, null, 120, TimeUnit.SECONDS);
        return vCode;
    }

    /**
     * 用户不在用户输入框的时候校验用户ID是否存在
     *
     * @param userId 用户ID
     */
    @GetMapping("checkUserId")
    public User checkUserIdOrUserName(@RequestParam Long userId) {
        if (userId == null) {
            throw new Err("用户ID不能为空");
        }
        User user = loginDao.checkUserId(userId);
        if (user == null) {
            throw new Err("用户不存在");
        } else {
            user.setPassword(null);
            user.setPrivateKey(null);
            return user;
        }
    }

    /**
     * 模拟前端加密
     *
     * @param password  用户输入的密码
     * @param salt      返回给前端的UUID盐
     * @param publicKey 返回给前端的RSA公钥
     */
    @IgnoreResAnnotate
    @GetMapping("front")
    public String front(@RequestParam String password, @RequestParam String salt, @RequestParam String publicKey) throws Exception {
        String passwordAndSaltForMD5 = Base64.getEncoder().encodeToString(MessageDigest.getInstance("MD5").digest((password + salt).getBytes(StandardCharsets.UTF_8)));//原始密码+UUID盐生成MD5
        return RSAUtil.encrypt(passwordAndSaltForMD5, publicKey.replaceAll(" ", "+"));//（原始密码+UUID盐生成MD5）+publicKey公钥再加密
    }

    /**
     * 登录
     *
     * @param userId   用户ID
     * @param password （初始密码+saltMD5加密）+RSA公钥加密后的密码
     */
    @PostMapping("login")
    public TokenInfo login(@RequestParam Long userId, @RequestParam String password, @RequestParam String vCode) {
        User user = loginDao.checkUserId(userId);
        if (user == null) {
            throw new Err("用户不存在");
        } else if (!RSAUtil.decrypt(password.replaceAll(" ", "+"), user.getPrivateKey()).equals(user.getPassword())) {
            throw new Err("密码错误");
        } else if (!redisTemplate.hasKey(vCode)) {
            throw new Err("验证码错误或已失效！");
        } else {
            //删除验证成功的验证码
            redisTemplate.delete(vCode);
            //把当前登录用户的信息存储到redis并返回给前端
            String token = UUID.randomUUID().toString();
            List<Role> roles = loginDao.selectUserRoles(user.getId());//角色组
            //超级管理员角色可以获取全部菜单
            boolean root = roles.size() > 0 && roles.stream().anyMatch(role -> role.getId() == 1);
            Set<Menu> menus = roles.size() <= 0 ? null : root ? loginDao.selectAllMenu() : loginDao.selectUserMenus(roles);//无重复菜单组
            Set<String> empowers = (menus == null || menus.size() <= 0) ? null : menus.stream().map(Menu::getPath).collect(Collectors.toSet());//无重复权限组
            TokenInfo tokenInfo = new TokenInfo(token, user.getId(), user.getName(), loginDao.selectUserDepts(user.getId()), roles, menus, empowers);
            redisTemplate.opsForValue().set(token, tokenInfo, tokenOvertime, TimeUnit.SECONDS);//把当前登录用户的信息存储到redis
            return tokenInfo;
        }
    }

    /**
     * 登出
     *
     * @param token
     */
    @PostMapping("logout")
    public String logout(@RequestParam String token) {
        if (!StringUtils.hasLength(token)) {
            throw new Err("token不能为空");
        } else if (redisTemplate.hasKey(token)) {
            throw new Err("token不存在");
        } else {
            redisTemplate.delete(token);
            return "登出成功！";
        }
    }
}
