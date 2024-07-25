package com.fu.basedemo;

import com.fu.basedemo.designpattern.singleton.usecase.DatabaseAccess;
import com.fu.basedemo.entity.User;
import org.junit.jupiter.api.Test;

/**
 * 单例模式使用场景测试
 *
 * @since 2024-07-25
 */
public class SingletonUseCaseTests {

    /**
     * 测试数据库连接池
     */
    @Test
    void testSingletonUseCase() {
        DatabaseAccess databaseAccess = new DatabaseAccess();
        System.out.println(databaseAccess.getUsers(User.class));;
    }

}
