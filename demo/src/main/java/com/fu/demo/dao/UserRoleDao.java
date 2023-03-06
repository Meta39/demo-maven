package com.fu.demo.dao;

import com.fu.demo.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleDao {

    //根据ID查询
    UserRole select(Long id);

    //查询全部
    List<UserRole> selectAll();

    //新增
    int insert(UserRole userRole);

    //更新
    int update(UserRole userRole);

    //删除
    int delete(Long id);

}

