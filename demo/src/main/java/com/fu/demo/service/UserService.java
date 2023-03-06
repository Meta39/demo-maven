package com.fu.demo.service;

import com.fu.demo.entity.User;
import com.fu.demo.dao.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    //根据ID查询
    public User select(Long id) {
        return userDao.select(id);
    }

    //查询全部
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    //新增
    public Integer insert(User user) {
        return userDao.insert(user);
    }

    //更新
    public Integer update(User user) {
        return userDao.update(user);
    }

    //删除
    public Integer delete(Long id) {
        return userDao.delete(id);
    }
}

