package com.fu.springbootdynamicdatasource.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeDataSourceServiceImpl {
    private final ChangeDataSourceOne changeDataSourceOne;
    private final ChangeDataSourceTwo changeDataSourceTwo;

    @Transactional//默认抛出RuntimeException才会回滚事务，如果抛出的是Exception则不会回滚事务！
    public void change(){
        changeDataSourceOne.useDefaultTransactionManager();
        changeDataSourceTwo.useSecondTransactionManager();
    }
}
