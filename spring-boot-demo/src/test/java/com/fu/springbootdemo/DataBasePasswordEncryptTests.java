package com.fu.springbootdemo;

import com.fu.springbootdemo.util.DataBasePasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 模拟对原始密码加密存放到数据库
 */
//@SpringBootTest
public class DataBasePasswordEncryptTests {

    @Test
    public void test(){
        String encryptPassword = DataBasePasswordUtil.encrypt("user", "salt");
        String decryptPassword = DataBasePasswordUtil.decrypt(encryptPassword, "salt");
        System.out.println(encryptPassword);
        System.out.println("user".equals(decryptPassword));
    }
}
