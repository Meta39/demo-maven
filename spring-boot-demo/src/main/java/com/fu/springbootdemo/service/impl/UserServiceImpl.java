package com.fu.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fu.springbootdemo.entity.User;
import com.fu.springbootdemo.mapper.UserMapper;
import com.fu.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据ID查询用户
     */
    @Override
    public User selectUserById(Integer id) {
        return this.userMapper.selectById(id);
    }

    /**
     * 新增用户
     */
    @Override
    public int insertUser(User user) {
        return this.userMapper.insert(user);
    }

    /**
     * 更新用户
     */
    @Override
    public int updateUser(User user) {
        return this.userMapper.updateById(user);
    }

    /**
     * 根据ID删除用户
     */
    @Override
    public int deleteUserById(Integer id) {
        return this.userMapper.deleteById(id);
    }

    /**
     * 分页查询用户
     */
    @Override
    public Page<User> selectUserPage(Long page, Long size) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        return this.userMapper.selectPage(Page.of(page, size), lqw);
    }

    /**
     * 查询用户
     * 列表
     */
    @Override
    public List<User> selectUserList(User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        return this.userMapper.selectList(lqw);
    }

    /**
     * 根据ID集合批量删除用户
     */
    @Override
    public int deleteUserByIds(List<Integer> ids) {
        return this.userMapper.deleteBatchIds(ids);
    }

    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     */
    @Override
    public User selectUserByUsername(String username){
        return this.userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username));
    }

    /**
     * 查询登录用户角色信息
     * @param userId 用户ID
     */
    @Override
    public Set<Integer> selectUserRoleIds(Integer userId) {
        return this.userMapper.selectUserRoleInfo(userId);
    }

    /**
     * 查询登录用户权限信息
     * @param roleIds 角色ID集合
     */
    @Override
    public Set<String> selectUserAuthorizes(Set<Integer> roleIds) {
        return this.userMapper.selectUserAuthorizes(roleIds);
    }

}
