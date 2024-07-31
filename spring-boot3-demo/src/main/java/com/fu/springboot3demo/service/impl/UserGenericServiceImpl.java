package com.fu.springboot3demo.service.impl;

import com.fu.springboot3demo.dto.UserDTO;
import com.fu.springboot3demo.entity.User;
import com.fu.springboot3demo.service.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户
 * @since 2024-07-16
 */
@Slf4j
@Service("user")
public class UserGenericServiceImpl implements GenericService<UserDTO> {

    @Override
    public User invoke(UserDTO dto) {
        log.info("UserDTO:{}", dto);
        User user = new User();
        user.setId(1L);
        user.setName("Meta39");
        return user;
    }

}