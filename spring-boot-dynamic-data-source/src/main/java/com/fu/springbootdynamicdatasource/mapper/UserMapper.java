package com.fu.springbootdynamicdatasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fu.springbootdynamicdatasource.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
