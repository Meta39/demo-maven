package com.fu.demo.dao;

import com.fu.demo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDao {

    //根据ID查询
    Menu select(Long id);

    //查询全部
    List<Menu> selectAll();

    //新增
    int insert(Menu menu);

    //更新
    int update(Menu menu);

    //删除
    int delete(Long id);

}

