package com.fu.demo.dao;

import com.fu.demo.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao {

    //根据ID查询
    Role select(Long id);

    //查询全部
    List<Role> selectAll();

    //新增
    int insert(Role role);

    //更新
    int update(Role role);

    //删除
    int delete(Long id);

}

