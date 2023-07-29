package com.fu.springbootdemo3out;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类以外的包，无法被扫描，如果要被扫描就要配置。如：以下方法选其一
 * 1、@SpringBootApplication(scanBasePackages = "com.fu.springbootdemo3out")
 * 2、@ComponentScan({"com.fu.springbootdemo3out"})
 */
@RestController
@RequestMapping("out")
public class OutController {

    @GetMapping
    public String out(){
        return "out";
    }

}
