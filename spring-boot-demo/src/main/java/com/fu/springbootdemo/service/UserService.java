package com.fu.springbootdemo.service;

import com.fu.springbootdemo.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface UserService extends IService<User> {
    /**
     * 根据ID查询用户
     */
    User selectUserById(Integer id);

    /**
     * 新增用户
     */
    int insertUser(User user);

    /**
     * 更新用户
     */
    int updateUser(User user);

    /**
     * 根据ID删除用户
     */
    int deleteUserById(Integer id);

    /**
     * 分页查询用户
     */
    Page<User> selectUserPage(Long page, Long size);

    /**
     * 查询用户
     * 列表
     */
    List<User> selectUserList(User user);

    /**
     * 根据ID集合批量删除用户
     */
    int deleteUserByIds(List<Integer> ids);

    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     */
    User selectUserByUsername(String username);

    /**
     * 查询登录用户角色信息
     * @param userId 用户ID
     */
    Set<Integer> selectUserRoleIds(Integer userId);

    /**
     * 查询登录用户权限信息
     * @param roleIds 角色ID集合
     */
    Set<String> selectUserAuthorizes(Set<Integer> roleIds);

}
