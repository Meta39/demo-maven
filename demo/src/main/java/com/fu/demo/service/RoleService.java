package com.fu.demo.service;

import com.fu.demo.entity.Role;
import com.fu.demo.dao.RoleDao;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class RoleService {
    @Resource
    private RoleDao roleDao;

    //根据ID查询
    public Role select(Long id) {
        return roleDao.select(id);
    }

    //查询全部
    public List<Role> selectAll() {
        return roleDao.selectAll();
    }

    //新增
    public Integer insert(Role role) {
        return roleDao.insert(role);
    }

    //更新
    public Integer update(Role role) {
        return roleDao.update(role);
    }

    //删除
    public Integer delete(Long id) {
        return roleDao.delete(id);
    }
}

