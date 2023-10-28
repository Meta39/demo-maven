package com.fu.springbootdynamicdatasource.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fu.springbootdynamicdatasource.entity.User;
import com.fu.springbootdynamicdatasource.mapper.UserMapper;
import com.fu.springbootdynamicdatasource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
