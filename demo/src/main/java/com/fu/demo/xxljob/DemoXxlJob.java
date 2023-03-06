package com.fu.demo.xxljob;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoXxlJob {

    @XxlJob("myJob")
    public void myJob(){
        log.info("this is my job.");
    }
}
