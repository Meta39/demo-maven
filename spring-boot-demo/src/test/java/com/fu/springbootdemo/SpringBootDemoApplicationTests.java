package com.fu.springbootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootDemoApplicationTests {

    @Test
    public void test(){
        String salt = "Salt";
        String saltPassword = "passwordSalt";
        int passwordLength = saltPassword.length() - salt.length();
        System.out.println(saltPassword.substring(0, passwordLength));
    }

}
