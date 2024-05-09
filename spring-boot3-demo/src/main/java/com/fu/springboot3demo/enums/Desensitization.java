package com.fu.springboot3demo.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * 脱敏枚举
 * 创建日期：2024-05-09
 */
public enum Desensitization {
    // 姓名
    USERNAME(username -> {
        int length = username.length();
        if(length == 2){
            return username.substring(0,1).concat("*");
        }else if(length == 3){
            return StringUtils.left(username,1).concat("*").concat(StringUtils.right(username,1));
        }else if(length > 3){
            return StringUtils.left(username,1).concat(generateAsterisk(username.substring(1,length-1).length())).concat(StringUtils.right(username,1));
        }else {
            return username;
        }
    }),
    // 身份证
    ID_CARD(idCard -> idCard.replaceAll("(\\d{3})\\d{12}(\\w{3})", "$1*****$2")),
    // 手机号
    PHONE(phone -> phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),
    // 地址
    ADDRESS(address -> address.replaceAll("(\\S{3})\\S{2}\\S*(\\S{2})", "$1********$2"));

    private final Function<String, String> serialize;

    Desensitization(Function<String, String> serialize) {
        this.serialize = serialize;
    }

    public Function<String, String> serialize() {
        return serialize;
    }

    private static String generateAsterisk(int length){
        return "*".repeat(Math.max(0, length));
    }

}
