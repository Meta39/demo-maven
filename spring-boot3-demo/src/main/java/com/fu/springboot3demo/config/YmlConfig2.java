package com.fu.springboot3demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义yml配置文件第一种写法
 */
@Data
@ConfigurationProperties(prefix = "pig2")
//@EnableConfigurationProperties(YmlConfig2.class)//这个注解必须放在@SpringBootApplication启动类，不能放在这，放在这会无法注入ioc容器。
public class YmlConfig2 {
    private String id;
    private String name;
}
