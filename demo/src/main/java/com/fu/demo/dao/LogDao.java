package com.fu.demo.dao;

import com.fu.demo.entity.Log;
import com.fu.demo.entity.dto.LogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogDao {

    /**
     * 创建日志表
     */
    int crateTable(@Param("tableName") String tableName);

    /**
     * 根据ID查询
     */
    Log queryById(@Param("tableName") String tableName, @Param("id") Long id);

    /**
     * 查询全部
     */
    List<Log> findAll(@Param("tableName") String tableName,@Param("logDTO") LogDTO logDTO);

    /**
     * 新增
     */
    int insert(@Param("tableName") String tableName,@Param("log") Log log);

}
