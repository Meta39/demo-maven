package com.fu.springbootstarter3demo.annotation;

import com.fu.springbootstarter3demo.StarterAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({StarterAutoConfiguration.class})//导入配置文件
public @interface EnableStarter {

}
