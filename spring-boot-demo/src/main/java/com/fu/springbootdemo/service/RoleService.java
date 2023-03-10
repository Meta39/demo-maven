package com.fu.springbootdemo.service;

import com.fu.springbootdemo.entity.Role;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface RoleService extends IService<Role> {
    /**
     * 根据ID查询角色
     */
    Role selectRoleById(Integer id);

    /**
     * 新增角色
     */
    int insertRole(Role role);

    /**
     * 更新角色
     */
    int updateRole(Role role);

    /**
     * 根据ID删除角色
     */
    int deleteRoleById(Integer id);

    /**
     * 分页查询角色
     */
    Page<Role> selectRolePage(Long page, Long size);

    /**
     * 查询角色
     * 列表
     */
    List<Role> selectRoleList(Role role);

    /**
     * 根据ID集合批量删除角色
     */
    int deleteRoleByIds(List<Integer> ids);

    /**
     * 根据角色ID集合查询角色名称
     * @param roleIds 角色ID集合
     */
    Set<String> selectRoleNamesByIds(Set<Integer> roleIds);
}
