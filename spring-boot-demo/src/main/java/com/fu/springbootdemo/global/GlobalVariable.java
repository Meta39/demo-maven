package com.fu.springbootdemo.global;

/**
 * 全局变量
 */
public class GlobalVariable {
    public static final String TOKEN = "token";//请求头token名称

    public static final String ENCRYPT_TYPE = "RSA"; //加密类型：RSA非对称加密
    public static final String HEAD_SALT = "headSalt"; //密码头部盐
    public static final String TAIL_SALT = "tailSalt"; //密码尾部盐

    public static final String RSA_PUBLIC_KEY = "rsaPublicKey"; //RSA加密公钥名称
    public static final String RSA_PRIVATE_KEY = "rsaPrivateKey"; //RSA私钥名称

}
