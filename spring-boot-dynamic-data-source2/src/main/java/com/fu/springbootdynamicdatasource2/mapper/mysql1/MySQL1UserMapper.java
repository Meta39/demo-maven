package com.fu.springbootdynamicdatasource2.mapper.mysql1;

import com.fu.springbootdynamicdatasource2.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 数据源1
 */
public interface MySQL1UserMapper {
    User selectById(@Param("id") Integer id);
}