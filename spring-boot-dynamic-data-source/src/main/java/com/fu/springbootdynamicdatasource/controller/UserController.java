package com.fu.springbootdynamicdatasource.controller;

import com.fu.springbootdynamicdatasource.annotation.ChangeDataSource;
import com.fu.springbootdynamicdatasource.entity.User;
import com.fu.springbootdynamicdatasource.enums.DataSources;
import com.fu.springbootdynamicdatasource.service.UserService;
import com.fu.springbootdynamicdatasource.serviceimpl.ChangeDataSourceTwo;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
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
    private final ChangeDataSourceTwo changeDataSourceTwo;

    /**
     * 使用第1个数据源
     * @param id id
     */
    @ChangeDataSource(DataSources.ONE)
    @Transactional(transactionManager = "defaultTransactionManager")
    @GetMapping("one/{id}")
    public User selectByOne(@PathVariable Integer id){
        return userService.getById(id);
    }

    /**
     * 使用第2个数据源
     * @param id id
     */
    @ChangeDataSource(DataSources.TWO)
    @Transactional(transactionManager = "secondTransactionManager")
    @GetMapping("two/{id}")
    public User selectByTwo(@PathVariable Integer id){
//        throw new RuntimeException();
        return userService.getById(id);
    }

    @Transactional(transactionManager = "defaultTransactionManager")
    @GetMapping("cross")
    public void cross(){
        User user = new User();
        user.setName("哈哈");
        userService.insert(user);
        changeDataSourceTwo.useSecondTransactionManager();
        throw new RuntimeException();//抛出异常主、从库数据都不会插入数据，证明事务控制成功。
    }

}