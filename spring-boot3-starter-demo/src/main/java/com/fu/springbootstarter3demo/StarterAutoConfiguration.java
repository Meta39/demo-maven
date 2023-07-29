package com.fu.springbootstarter3demo;

import com.fu.springbootstarter3demo.controller.StarterController;
import com.fu.springbootstarter3demo.properties.StarterProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 给容器中导入需要使用的组件，然后在META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
 * 导入com.fu.springbootstarter3demo.StarterAutoConfiguration，这样就实现了starter组件自动配置。
 * 只需要引入相关依赖，不需要任何配置即可实现Spring Boot自动配置功能。
 */
@Import({StarterController.class, StarterProperties.class})
@Configuration
public class StarterAutoConfiguration {

}
