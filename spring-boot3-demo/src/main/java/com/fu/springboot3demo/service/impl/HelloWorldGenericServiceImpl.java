package com.fu.springboot3demo.service.impl;

import com.fu.springboot3demo.dto.GenericServiceDTO;
import com.fu.springboot3demo.service.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @since 2024-07-16
 */
@Slf4j
@Service("helloWorld")
public class HelloWorldGenericServiceImpl implements GenericService<GenericServiceDTO> {

    @Override
    public String invoke(GenericServiceDTO dto) {
        log.info("GenericServiceDto: {}", dto);
        return "Hello World";
    }

}