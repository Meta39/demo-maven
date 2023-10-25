package com.fu;

import com.fu.frame.MainWin;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TransformApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TransformApplication.class);
        ApplicationContext context = builder.headless(false).web(WebApplicationType.NONE).run(args);
        MainWin mainWin = context.getBean(MainWin.class);
        mainWin.init();
    }

}