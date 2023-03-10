package com.fu.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fu.springbootdemo.entity.Role;
import com.fu.springbootdemo.entity.User;
import com.fu.springbootdemo.entity.UserRole;
import com.fu.springbootdemo.global.Err;
import com.fu.springbootdemo.mapper.RoleMapper;
import com.fu.springbootdemo.mapper.UserMapper;
import com.fu.springbootdemo.mapper.UserRoleMapper;
import com.fu.springbootdemo.service.UserService;
import com.fu.springbootdemo.util.DataBasePasswordUtil;
import com.fu.springbootdemo.util.GeneratorRandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

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
        if (StringUtils.hasLength(user.getSalt())) {
            throw Err.setMessage("新增用户时不允许传盐");
        }
        if (!StringUtils.hasLength(user.getPwd())) {
            throw Err.setMessage("密码不能为空");
        }
        return this.userMapper.insert(setSaltAndPassword(user));
    }

    /**
     * 更新用户
     */
    @Override
    public int updateUser(User user) {
        if (StringUtils.hasLength(user.getSalt())) {
            throw Err.setMessage("更新用户时不允许传盐");
        }
        return this.userMapper.updateById(setSaltAndPassword(user));
    }

    /**
     * 根据ID删除用户
     */
    @Override
    public int deleteUserById(Integer id) {
        if (id == 1) {
            throw Err.setMessage("不允许删除超级用户");
        }
        return this.userMapper.deleteById(id);
    }

    /**
     * 分页查询用户
     */
    @Override
    public Page<User> selectUserPage(Long page, Long size) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        Page<User> userPage = this.userMapper.selectPage(Page.of(page, size), lqw);
        userPage.getRecords().forEach(user -> {
            //获取用户列表对应的角色列表集合
            List<Integer> roleIds = this.userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId())).stream()
                    .map(UserRole::getRoleId).collect(Collectors.toList());
            if (!roleIds.isEmpty()){
                user.setRoles(new HashSet<>(this.roleMapper.selectBatchIds(roleIds)));
            }
        });
        return userPage;
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
        if (ids != null && !ids.isEmpty() && ids.stream().anyMatch(id -> id == 1)) {
            throw Err.setMessage("不允许删除超级用户");
        }
        return this.userMapper.deleteBatchIds(ids);
    }

    //---------------------------------------内部方法--------------------------------------------------

    /**
     * 设置盐和加密密码
     * @param user 用户
     */
    private User setSaltAndPassword(User user){
        if (StringUtils.hasLength(user.getPwd())){
            //设置新增用户的盐
            String salt = GeneratorRandomUtil.getRandomLengthStringAndNumbers();
            user.setSalt(salt);
            //新增用户密码加盐加密
            String password = DataBasePasswordUtil.encrypt(user.getPwd(),salt);
            user.setPwd(password);
        }
        return user;
    }

}
