package com.fu.demo.dao;

import com.fu.demo.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMenuDao {

    //根据ID查询
    RoleMenu select(Long id);

    //查询全部
    List<RoleMenu> selectAll();

    //新增
    int insert(RoleMenu roleMenu);

    //更新
    int update(RoleMenu roleMenu);

    //删除
    int delete(Long id);

}

