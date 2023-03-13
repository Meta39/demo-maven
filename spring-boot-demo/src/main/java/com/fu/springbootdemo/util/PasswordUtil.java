package com.fu.springbootdemo.util;

import com.fu.springbootdemo.global.Code;
import com.fu.springbootdemo.global.Err;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.*;

import static com.fu.springbootdemo.global.GlobalVariable.*;

/**
 * 密码加密工具类
 */
@Component
@ConfigurationProperties("fu.redis-password-key-timeout")
public class PasswordUtil {
    /**
     * 密码公私钥存放时间单位秒s
     */
    private int redisPasswordKeyTimeout = 300;
    private static final Logger log = LoggerFactory.getLogger(PasswordUtil.class);
    private static final int KEY_SIZE = 1024; //密钥长度 于原文长度对应 以及越长速度越慢
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;//把密钥对存放到Redis

    /**
     * 生成随机密钥对，存放到redis
     *
     * @param UUID 前端请求获取公钥时需要提供对应的UUID
     */
    public void generatorKeyPair(String UUID) {
        if (!StringUtils.hasLength(UUID)){
            throw Err.setMessage("UUID不能为空");
        }
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(ENCRYPT_TYPE);
        } catch (NoSuchAlgorithmException e) {
            log.error("获取加密算法"+ENCRYPT_TYPE+"单例异常：",e);
            throw Err.setCodeAndMessage(Code.ENCRYPT_ALGORITHM_ERROR.getErrCode(),Code.ENCRYPT_ALGORITHM_ERROR.getErrMessage());
        }
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        // 将公钥和私钥保存到redis并设置过期时间
        Map<String, String> map = new HashMap<>(8);
        map.put(RSA_PUBLIC_KEY, publicKeyString);
        map.put(RSA_PRIVATE_KEY, privateKeyString);
        map.put(HEAD_SALT,GeneratorRandomUtil.getRandomLengthStringAndNumbers());//密码头部盐
        map.put(TAIL_SALT,GeneratorRandomUtil.getRandomLengthStringAndNumbers());//密码尾部盐
        //set值如果redis存在该uuid则不进行任何操作。（概率比较小，但是不排除前端会请求多次，如果用set()那么前端请求多次，redis里的值就会不一样。）
        redisTemplate.opsForValue().setIfAbsent(ENCRYPT_TYPE + ":" + UUID, map, Duration.ofSeconds(getRedisPasswordKeyTimeout()));
    }

    /**
     * 公钥加密
     * @param UUID 通过redis获取对应的公钥和盐
     * @param noSaltPassword 原始密码
     */
    public String encrypt(String UUID,String noSaltPassword) {
        Map<String, String> map = checkUUID(UUID);
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getMimeDecoder().decode(map.get(RSA_PUBLIC_KEY));
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ENCRYPT_TYPE).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            String password = map.get(HEAD_SALT) + noSaltPassword + map.get(TAIL_SALT);//密码头尾加盐
            log.info("原始密码：noSaltPassword：{} 头尾加盐：{},{} 》》》 password：{}",noSaltPassword,map.get(HEAD_SALT),map.get(TAIL_SALT),password);
            return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("加密失败：", e);
            throw Err.setCodeAndMessage(Code.ENCRYPT_ERROR.getErrCode(), Code.ENCRYPT_ERROR.getErrMessage());
        }
    }

    /**
     * 私钥解密
     * @param UUID 通过redis获取对应的私钥
     * @param encryptPassword 加密密码
     */
    public String decrypt(String UUID,String encryptPassword) {
        Map<String, String> map = checkUUID(UUID);
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getMimeDecoder().decode(encryptPassword);
            //base64编码的私钥
            byte[] decoded = Base64.getMimeDecoder().decode(map.get(RSA_PRIVATE_KEY));
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ENCRYPT_TYPE).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String saltPassword = new String(cipher.doFinal(inputByte), StandardCharsets.UTF_8);//加盐密码
            String headSalt = map.get(HEAD_SALT);//头部盐
            String tailSalt = map.get(TAIL_SALT);//尾部盐
            saltPassword = saltPassword.substring(headSalt.length());//去除头部盐
            String password =saltPassword.substring(0,saltPassword.length() - tailSalt.length());//去除尾部盐
            log.info("加盐密码：{}，头部盐：{}，原始密码：{}，尾部盐：{}",saltPassword,map.get(HEAD_SALT),password,map.get(TAIL_SALT));
            return password;//切割掉随机长度的盐就是原始密码
        } catch (Exception e) {
            log.error("解密失败：", e);
            throw Err.setCodeAndMessage(Code.DECRYPT_ERROR.getErrCode(), Code.DECRYPT_ERROR.getErrMessage());
        }
    }

    /**
     * 校验redis里的密码key是否存在
     */
    public Map<String,String> checkUUID(String UUID){
        if (!StringUtils.hasLength(UUID)) {
            throw Err.setMessage("UUID不能为空");
        }
        String redisRsaKey = ENCRYPT_TYPE + ":" + UUID;
        Boolean hasKey = this.redisTemplate.hasKey(redisRsaKey);
        if (hasKey == null || !hasKey){
            //密钥对在redis已过期
            throw Err.setCodeAndMessage(Code.KEY_PAIR_TIMEOUT_ERROR.getErrCode(), Code.KEY_PAIR_TIMEOUT_ERROR.getErrMessage());
        }
        Map<String,String> map = (Map<String, String>) this.redisTemplate.opsForValue().get(redisRsaKey);
        if (map == null || map.isEmpty()){
            throw Err.setMessage("对应UUID："+UUID+"的Redis密钥对是空的");
        }
        return map;
    }

    //---------------------------get/set--------------------------------------


    public int getRedisPasswordKeyTimeout() {
        return redisPasswordKeyTimeout;
    }

    public void setRedisPasswordKeyTimeout(int redisPasswordKeyTimeout) {
        this.redisPasswordKeyTimeout = redisPasswordKeyTimeout;
    }
}
