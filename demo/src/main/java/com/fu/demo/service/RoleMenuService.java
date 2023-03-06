package com.fu.demo.service;

import com.fu.demo.entity.RoleMenu;
import com.fu.demo.dao.RoleMenuDao;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class RoleMenuService {
    @Resource
    private RoleMenuDao roleMenuDao;

    //根据ID查询
    public RoleMenu select(Long id) {
        return roleMenuDao.select(id);
    }

    //查询全部
    public List<RoleMenu> selectAll() {
        return roleMenuDao.selectAll();
    }

    //新增
    public Integer insert(RoleMenu roleMenu) {
        return roleMenuDao.insert(roleMenu);
    }

    //更新
    public Integer update(RoleMenu roleMenu) {
        return roleMenuDao.update(roleMenu);
    }

    //删除
    public Integer delete(Long id) {
        return roleMenuDao.delete(id);
    }
}

