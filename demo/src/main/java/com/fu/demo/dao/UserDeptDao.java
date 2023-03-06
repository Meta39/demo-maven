package com.fu.demo.dao;

import com.fu.demo.entity.UserDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDeptDao {

    //根据ID查询
    UserDept select(Long id);

    //查询全部
    List<UserDept> selectAll();

    //新增
    int insert(UserDept userDept);

    //更新
    int update(UserDept userDept);

    //删除
    int delete(Long id);

}

