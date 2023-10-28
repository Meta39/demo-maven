package com.fu.springbootdynamicdatasource.controller;

import com.fu.springbootdynamicdatasource.annotation.ChangeDataSource;
import com.fu.springbootdynamicdatasource.entity.User;
import com.fu.springbootdynamicdatasource.enums.DataSources;
import com.fu.springbootdynamicdatasource.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义注解 + AOP 实现动态切换数据源
 */
@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 使用第1个数据源
     * @param id id
     */
    @ChangeDataSource(DataSources.ONE)
    @GetMapping("one/{id}")
    public User selectByOne(@PathVariable Integer id){
        return userService.getById(id);
    }

    /**
     * 使用第2个数据源
     * @param id id
     */
    @ChangeDataSource(DataSources.TWO)
    @GetMapping("two/{id}")
    public User selectByTwo(@PathVariable Integer id){
//        throw new RuntimeException();
        return userService.getById(id);
    }

}