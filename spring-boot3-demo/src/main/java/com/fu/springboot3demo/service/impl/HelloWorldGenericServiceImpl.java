package com.fu.springboot3demo.service.impl;

import com.fu.springboot3demo.dto.GenericServiceDto;
import com.fu.springboot3demo.service.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @since 2024-07-16
 */
@Slf4j
@Service("helloWorld")
public class HelloWorldGenericServiceImpl implements GenericService<GenericServiceDto, String> {

    @Override
    public String invoke(GenericServiceDto request) {
        return "Hello World";
    }

}