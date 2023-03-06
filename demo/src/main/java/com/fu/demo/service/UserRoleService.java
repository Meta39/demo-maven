package com.fu.demo.service;

import com.fu.demo.entity.UserRole;
import com.fu.demo.dao.UserRoleDao;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserRoleService {
    @Resource
    private UserRoleDao userRoleDao;

    //根据ID查询
    public UserRole select(Long id) {
        return userRoleDao.select(id);
    }

    //查询全部
    public List<UserRole> selectAll() {
        return userRoleDao.selectAll();
    }

    //新增
    public Integer insert(UserRole userRole) {
        return userRoleDao.insert(userRole);
    }

    //更新
    public Integer update(UserRole userRole) {
        return userRoleDao.update(userRole);
    }

    //删除
    public Integer delete(Long id) {
        return userRoleDao.delete(id);
    }
}

