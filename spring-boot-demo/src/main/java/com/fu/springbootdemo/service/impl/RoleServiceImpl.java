package com.fu.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fu.springbootdemo.entity.Role;
import com.fu.springbootdemo.mapper.RoleMapper;
import com.fu.springbootdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据ID查询角色
     */
    @Override
    public Role selectRoleById(Integer id) {
        return this.roleMapper.selectById(id);
    }

    /**
     * 新增角色
     */
    @Override
    public int insertRole(Role role) {
        return this.roleMapper.insert(role);
    }

    /**
     * 更新角色
     */
    @Override
    public int updateRole(Role role) {
        return this.roleMapper.updateById(role);
    }

    /**
     * 根据ID删除角色
     */
    @Override
    public int deleteRoleById(Integer id) {
        return this.roleMapper.deleteById(id);
    }

    /**
     * 分页查询角色
     */
    @Override
    public Page<Role> selectRolePage(Long page, Long size) {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        return this.roleMapper.selectPage(Page.of(page, size), lqw);
    }

    /**
     * 查询角色
     * 列表
     */
    @Override
    public List<Role> selectRoleList(Role role) {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        return this.roleMapper.selectList(lqw);
    }

    /**
     * 根据ID集合批量删除角色
     */
    @Override
    public int deleteRoleByIds(List<Integer> ids) {
        return this.roleMapper.deleteBatchIds(ids);
    }

    /**
     * 根据角色ID集合查询角色名称
     * @param roleIds 角色ID集合
     */
    @Override
    public Set<String> selectRoleNamesByIds(Set<Integer> roleIds) {
        List<Role> roles = this.roleMapper.selectBatchIds(roleIds);
        return roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
    }
}
