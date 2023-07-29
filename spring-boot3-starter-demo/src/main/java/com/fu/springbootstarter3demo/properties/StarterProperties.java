package com.fu.springbootstarter3demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件
 */
@Data
@Component
@ConfigurationProperties(prefix = "meta.starter")
public class StarterProperties {
    private String name = "Meta39";
    private Integer age = 25;
}
