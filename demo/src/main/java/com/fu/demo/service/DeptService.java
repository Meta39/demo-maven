package com.fu.demo.service;

import com.fu.demo.entity.Dept;
import com.fu.demo.dao.DeptDao;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class DeptService {
    @Resource
    private DeptDao deptDao;

    //根据ID查询
    public Dept select(Long id) {
        return deptDao.select(id);
    }

    //查询全部
    public List<Dept> selectAll() {
        return deptDao.selectAll();
    }

    //新增
    public Integer insert(Dept dept) {
        return deptDao.insert(dept);
    }

    //更新
    public Integer update(Dept dept) {
        return deptDao.update(dept);
    }

    //删除
    public Integer delete(Long id) {
        return deptDao.delete(id);
    }
}

