package com.fu.springboot3demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义yml配置文件第一种写法
 */
@Data
@Component
@ConfigurationProperties(prefix = "pig")
public class YmlConfig {
    private String id;
    private String name;
}
