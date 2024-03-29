package com.fu.springbootdynamicdatasource.serviceimpl;

import com.fu.springbootdynamicdatasource.annotation.ChangeDataSource;
import com.fu.springbootdynamicdatasource.entity.User;
import com.fu.springbootdynamicdatasource.enums.DataSources;
import com.fu.springbootdynamicdatasource.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeDataSourceTwo {
    private final UserMapper userMapper;

    @ChangeDataSource(DataSources.TWO)
    public void useSecondTransactionManager() {
        User user = new User();
        user.setName("hehe");
        userMapper.insert(user);
//        throw new RuntimeException();//如果抛出异常，则不会往2个数据库插入数据，验证事务成功
    }

}
