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
public class ChangeDataSourceOne {
    private final UserMapper userMapper;

    @ChangeDataSource(DataSources.ONE)
    public void useDefaultTransactionManager() {
        User user = new User();
        user.setName("哈哈");
        userMapper.insert(user);
//        throw new RuntimeException();//如果抛出异常，则不会往2个数据库插入数据，验证事务成功
    }

}
