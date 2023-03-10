package com.fu.springbootdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fu.springbootdemo.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    //查询用户角色集合信息
    @Select("SELECT id FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id = #{userId})")
    Set<Integer> selectUserRoleInfo(@Param("userId") Integer userId);

    //获取用户角色集合的权限集合
    @Select("SELECT authorize_name FROM authorize WHERE id IN(SELECT authorize_id FROM role_authorize WHERE role_id IN (#{roleIds}))")
    Set<String> selectUserAuthorizes(@Param("roleIds") Set<Integer> roleIds);

}
