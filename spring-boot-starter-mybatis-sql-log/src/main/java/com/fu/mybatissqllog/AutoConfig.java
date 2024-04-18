package com.fu.mybatissqllog;

import com.fu.mybatissqllog.config.MyBatisSqlLogConfig;
import com.fu.mybatissqllog.plugin.MyBatisSqlParsingPlugin;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MyBatisSqlLogConfig.class})
//指定的参数值符合要求时，对应的配置才生效
@ConditionalOnProperty(prefix = "mybatis-log", value = "enabled", matchIfMissing = true)
public class AutoConfig {

    @Bean
    //不包含这个类，这个类才会注入到Spring容器中
    @ConditionalOnMissingBean
    public MyBatisSqlParsingPlugin myBatisSqlParsingPlugin() {
        return new MyBatisSqlParsingPlugin();
    }

}