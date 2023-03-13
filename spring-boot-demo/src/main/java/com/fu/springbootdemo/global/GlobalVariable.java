package com.fu.springbootdemo.global;

/**
 * 全局变量
 */
public class GlobalVariable {
    public static final String TOKEN = "token";//请求头token名称

    public static final String ENCRYPT_TYPE = "RSA"; //加密类型：RSA非对称加密
    public static final String PASSWORD_SALT = "passwordSalt"; //盐

    public static final String RSA_PUBLIC_KEY = "rsaPublicKey"; //RSA加密公钥名称
    public static final String RSA_PRIVATE_KEY = "rsaPrivateKey"; //RSA私钥名称

}
