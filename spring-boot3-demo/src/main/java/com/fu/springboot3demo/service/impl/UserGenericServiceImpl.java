package com.fu.springboot3demo.service.impl;

import com.fu.springboot3demo.dto.GenericServiceDto;
import com.fu.springboot3demo.entity.User;
import com.fu.springboot3demo.service.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 无返回值接口
 * @since 2024-07-16
 */
@Slf4j
@Service("user")
public class UserGenericServiceImpl implements GenericService<GenericServiceDto, User> {

    @Override
    public User invoke(GenericServiceDto request) {
        User user = new User();
        user.setId(1L);
        user.setName("Meta39");
        return user;
    }

}