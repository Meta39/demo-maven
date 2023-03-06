package com.fu.demo.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * Java RSA 加密工具类
 */
@Slf4j
public class RSAUtil {

    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;
    /**
     * 用于封装随机产生的公钥与私钥
     */
    private static Map<Integer, String> keyMap = new HashMap<>();
    /**
     * 随机生成密钥对
     */
    public static Map<String, String> genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
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
        // 将公钥和私钥保存到Map
        Map map = new HashMap(2);
        map.put("publicKey",publicKeyString);
        map.put("privateKey",privateKeyString);
        //0表示公钥
        keyMap.put(0, publicKeyString);
        //1表示私钥
        keyMap.put(1, privateKeyString);
        return map;
    }
    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey)  {
        String outStr = "";
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getMimeDecoder().decode(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        }catch (Exception e){
            log.error("加密异常",e);
        }
        return outStr;
    }
    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) {
        String outStr = "";
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getMimeDecoder().decode(str);
            //base64编码的私钥
            byte[] decoded = Base64.getMimeDecoder().decode(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte),StandardCharsets.UTF_8);
        }catch (Exception e){
            log.error("解密异常",e);
        }
        return outStr;
    }
    public static void main(String[] args) throws Exception {
        long temp = System.currentTimeMillis();
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
        log.debug("公钥:" + keyMap.get(0));
        log.debug("公钥:" + keyMap.get(0));
        log.debug("私钥:" + keyMap.get(1));
        log.debug("生成密钥消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
        String password = "123456";
        log.debug("原始密码："+password);
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        log.debug("UUID盐："+salt);
        String message = Base64.getEncoder().encodeToString(MessageDigest.getInstance("MD5").digest((password+salt).getBytes(StandardCharsets.UTF_8)));//MD5加密密码
//        String message = "x";
        log.debug("MD5加密（原始密码+salt）的密文:" + message);
        temp = System.currentTimeMillis();
        String messageEn = encrypt(message, keyMap.get(0));
        log.debug("RSA公钥加密后的密文:" + messageEn);
        log.debug("加密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
        temp = System.currentTimeMillis();
        String messageDe = decrypt(messageEn, keyMap.get(1));
        log.debug("RSA私钥解密成MD5加密（原始密码+salt）的密文:" + messageDe);
        log.debug("解密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
    }
}

