package com.fu.demo.dao;

import com.fu.demo.entity.UserDetails;

import com.fu.demo.entity.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDetailsDao {

    /**
     * 根据ID查询
     */
    UserDetails queryById(@Param("id") Long id);

    /**
     * 查询全部
     */
    List<UserDetails> findAll();

    /**
     * 新增
     */
    int insert(UserDetails userDetails);

    /**
     * 更新
     */
    int update(UserDetails userDetails);

    /**
     * 删除
     */
    int deleteById(@Param("id") Long id);

    UserVO queryByUserId(@Param("userId") Long userId);

}
