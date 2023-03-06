package com.fu.demo.service;

import com.fu.demo.entity.UserDept;
import com.fu.demo.dao.UserDeptDao;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserDeptService {
    @Resource
    private UserDeptDao userDeptDao;

    //根据ID查询
    public UserDept select(Long id) {
        return userDeptDao.select(id);
    }

    //查询全部
    public List<UserDept> selectAll() {
        return userDeptDao.selectAll();
    }

    //新增
    public Integer insert(UserDept userDept) {
        return userDeptDao.insert(userDept);
    }

    //更新
    public Integer update(UserDept userDept) {
        return userDeptDao.update(userDept);
    }

    //删除
    public Integer delete(Long id) {
        return userDeptDao.delete(id);
    }
}

