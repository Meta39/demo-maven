package com.fu.demo.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendEmailController {
    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送邮件
     */
    @PostMapping("sendEmail")
    public void sendEmail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("5399553@qq.com"); //设置发送邮件账号
        simpleMailMessage.setTo("94538145@qq.com"); //设置接收邮件的人，可以多个
        simpleMailMessage.setSubject("测试邮箱发送"); //设置发送邮件的主题
        simpleMailMessage.setText("验证码为：123456"); //设置发送邮件的内容
        javaMailSender.send(simpleMailMessage);
    }
}
