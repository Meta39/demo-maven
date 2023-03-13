package com.fu.springbootdemo.util;

import com.fu.springbootdemo.global.Code;
import com.fu.springbootdemo.global.Err;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.fu.springbootdemo.global.GlobalVariable.*;

/**
 * 数据库密码加密工具类
 */
public class DataBasePasswordUtil {
    private static final Logger log = LoggerFactory.getLogger(DataBasePasswordUtil.class);
    private static final int KEY_SIZE = 1024; //密钥长度 于原文长度对应 以及越长速度越慢
    /**
     * 数据库密码公钥（设置值以后禁止再更改）
     */
    private static final String databaseRASPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHT6q7veWJMzgCKp1YXL0vicEoVN2XfnfGAdlrYxbAroDvMS+KKxFU3Wle3E+Uh16JYBEhY1QI5klJlPmmUKTy07txn5mQsA8JODhj0F9biucp8O4ZYAy43HAx6SUVmpkV+1hTeabT02aSUYYguq5LYMni5F3yYQszrHFXXsN0+wIDAQAB";
    /**
     * 数据库密码私钥（设置值以后禁止更改）
     */
    private static final String databaseRASPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIdPqru95YkzOAIqnVhcvS+JwShU3Zd+d8YB2WtjFsCugO8xL4orEVTdaV7cT5SHXolgESFjVAjmSUmU+aZQpPLTu3GfmZCwDwk4OGPQX1uK5ynw7hlgDLjccDHpJRWamRX7WFN5ptPTZpJRhiC6rktgyeLkXfJhCzOscVdew3T7AgMBAAECgYAXD+sHqKwQoFwxclXzE4tjKNV1LFHAWlvLsdD+zpq4KIXtPrLdl82Sa1JkSqR9qjzgqTECQc223pkrI2sRf7k9MllmKM5Df7WF4H6PHK8EdNL+WpaGS+pSWsW/C/Lw/irDYgmZNjJNCDXgYEfgLgwYz6YwFtyBEqnCLLW9wK0fcQJBAMlXd9m0AIxTg0C4cIG/ywFh12VCTGTjUbdt0dPQimGiv8fpYlihOZgCnAZHGqxtkr2QXREmFqycyvsz+v6evJ8CQQCsC1Cs5+TNH3Uk6hJ+Ye73FX+0Zm3QMr9bGD5mapHjqkHsLhmEIf5IV29kUDO2DlOeV9O5WjzOihNJgyj7jo4lAkEAoXiLfsXUNrX3Kl2ApR36ocYk52lvOO3xqCjjwXqasEjEg4ARRkEunlFhbctygdxydaKkcM9aGd65DxMV9UO6wwJAVv35sxQmexm28A1zgHggjstOIXfRev7OB0/A7aRhVE7YuoWs3UUbAJVFe+GZ5CpmWaBrLEPWj1D946PwaxJwCQJBAIFlNF5JqSJByKxQlmvKN6nWvdo16lPCGa/Ro7XyqKfYzA6B/ggaPfWukeMNdHVBYSxxuvx+ADKs1sWjwRIXe+Y=";
    /**
     * 数据库密码盐（设置值以后禁止更改）
     */
    private static final String databaseRASSalt = "A-z%$_1!.";

    /**
     * 生成数据库密钥对（databaseRASPublicKey、databaseRASPrivateKey），生成以后就不需要再生成了。
     */
    public static Map<String, String> generatorKeyPair() {
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
        map.put(RSA_PUBLIC_KEY,publicKeyString);
        map.put(RSA_PRIVATE_KEY,privateKeyString);
        log.info( "数据库RSA加密密钥对公钥：【{}】 》》》 私钥：【{}】",publicKeyString,privateKeyString);
        return map;
    }

    /**
     * 数据库密码加密
     * @param password 未加密的密码
     * @param salt 数据库里的盐
     */
    public static String encrypt(String password,String salt) {
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getMimeDecoder().decode(databaseRASPublicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ENCRYPT_TYPE).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            //数据库盐加原始密码再加固定盐加密成RSA密文(这里不能用随机盐，因为这里的盐是存在数据库里的。)
            String saltPassword = salt + password + databaseRASSalt;
            String passwordString = Base64.getEncoder().encodeToString(cipher.doFinal(saltPassword.getBytes(StandardCharsets.UTF_8)));
//            log.info("原始密码：{}，加盐加密后的密码密文：{}",password,passwordString);
            return passwordString;
        } catch (Exception e) {
            log.error("加密失败：", e);
            throw Err.setCodeAndMessage(Code.ENCRYPT_ERROR.getErrCode(), Code.ENCRYPT_ERROR.getErrMessage());
        }
    }

    /**
     * 数据库密码解密
     * @param encryptPassword 密码加密密文
     * @param salt 数据库里的盐
     */
    public static String decrypt(String encryptPassword,String salt) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getMimeDecoder().decode(encryptPassword);
            //base64编码的私钥
            byte[] decoded = Base64.getMimeDecoder().decode(databaseRASPrivateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ENCRYPT_TYPE).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String saltPassword = new String(cipher.doFinal(inputByte), StandardCharsets.UTF_8);
            saltPassword = saltPassword.substring(salt.length());//去除头部盐
            String password =saltPassword.substring(0,saltPassword.length() - databaseRASSalt.length());//去除尾部盐
//            log.info("原始密码：{}",password);
            return password;
        } catch (Exception e) {
            log.error("解密失败：", e);
            throw Err.setCodeAndMessage(Code.DECRYPT_ERROR.getErrCode(), Code.DECRYPT_ERROR.getErrMessage());
        }
    }

}
