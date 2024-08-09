package com.fu.springboot3demo.generic.impl;

import com.fu.springboot3demo.dto.GenericServiceDTO;
import com.fu.springboot3demo.generic.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @since 2024-07-16
 */
@Slf4j
@Service("helloWorld")
public class HelloWorldGenericServiceImpl implements GenericService<GenericServiceDTO> {

    @Override
    public String invoke(GenericServiceDTO req) {
        log.info("GenericServiceDto: {}", req);
        return "Hello World";
    }

}