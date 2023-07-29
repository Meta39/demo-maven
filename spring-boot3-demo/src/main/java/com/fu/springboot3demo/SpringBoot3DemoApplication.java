package com.fu.springboot3demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.springboot3demo.config.YmlConfig;
import com.fu.springboot3demo.config.YmlConfig2;
import com.fu.springboot3demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ObjectMapper.class)//@Import把第三方组件放入ioc，组件名称默认是全类名。
@SpringBootApplication//Spring Boot只会扫描主程序所在的包及其下面的子包
//@SpringBootApplication(scanBasePackages = "com.fu.springbootdemo3out")//配置扫描指定的包，解决包不在主程序所在的包及其下面的子包无法扫描的问题
@ComponentScan({"com.fu.springboot3demo", "com.fu.springbootdemo3out"})//配置扫描指定的包，解决包不在主程序所在的包及其下面的子包无法扫描的问题（推荐）
@EnableConfigurationProperties(YmlConfig2.class)//一般用于导入第三方写好的组件进行绑定
public class SpringBoot3DemoApplication {

    public static void main(String[] args) {
        //java10：var局部变量类型自动推断
        var ioc = SpringApplication.run(SpringBoot3DemoApplication.class, args);
        String[] names = ioc.getBeanDefinitionNames();
        StringBuilder beans = new StringBuilder();
        int i = 0;
        for (String name : names) {
            if (i % 5 == 0) {
                beans.append(name).append(",\n");
            } else {
                beans.append(name).append(",");
            }
            i++;
        }
        log.info("获取所有启动的Beans：{}", beans);
        //获取ioc容器里的Bean
        log.info("获取自定义的bean：{}",ioc.getBean("outController"));
        //多实例Bean。因为Bean默认单实例
        User user = (User) ioc.getBean("user");
        User user2 = (User) ioc.getBean("user");
        log.info("bean user 因为开启了@Scope(\"prototype\")多实例注解，因此两个对象不一样{}", user == user2);//因为MySpringBootConfig配置了@Scope("prototype")多实例注解，因此两个对象不一样。
        ObjectMapper om = (ObjectMapper) ioc.getBean("com.fasterxml.jackson.databind.ObjectMapper");//@Import获取第三方组件放入ioc的bean
        try {
            log.info("@Import获取第三方组件放入ioc的bean{}",om.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //获取自定义配置文件
        YmlConfig ymlConfig = ioc.getBean(YmlConfig.class);
        log.info("@Component + @ConfigurationProperties(prefix = \"pig\")：自定义配置文件ymlConfig：{}",ymlConfig);
        YmlConfig2 ymlConfig2 = ioc.getBean(YmlConfig2.class);
        log.info("@EnableConfigurationProperties(YmlConfig2.class)【@EnableConfigurationProperties注解必须放在启动类】 + @ConfigurationProperties(prefix = \"pig2\")：第三方配置文件ymlConfig2：{}",ymlConfig2);
    }

}
