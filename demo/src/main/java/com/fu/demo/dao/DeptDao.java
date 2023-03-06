package com.fu.demo.dao;

import com.fu.demo.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptDao {

    //根据ID查询
    Dept select(Long id);

    //查询全部
    List<Dept> selectAll();

    //新增
    int insert(Dept dept);

    //更新
    int update(Dept dept);

    //删除
    int delete(Long id);

}

