package com.fu.springbootdemo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fu.springbootdemo.annotation.PreAuthorize;
import com.fu.springbootdemo.entity.Role;
import com.fu.springbootdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 根据ID查询角色
     *
     * @param id ID
     */
    @PreAuthorize("role:select")
    @GetMapping("{id}")
    public Role selectRoleById(@PathVariable("id") Integer id) {
        return this.roleService.selectRoleById(id);
    }

    /**
     * 新增角色
     *
     * @param role 角色实体类
     */
    @PreAuthorize("role:insert")
    @PostMapping
    public Integer insertRole(@RequestBody @Valid Role role) {
        return this.roleService.insertRole(role);
    }

    /**
     * 更新角色
     *
     * @param role 角色实体类
     */
    @PreAuthorize("role:update")
    @PutMapping
    public Integer updateRole(@RequestBody @Valid Role role) {
        return this.roleService.updateRole(role);
    }

    /**
     * 根据ID删除角色
     *
     * @param id ID
     */
    @PreAuthorize("role:delete")
    @DeleteMapping("{id}")
    public Integer deleteRole(@PathVariable Integer id) {
        return this.roleService.deleteRoleById(id);
    }

    /**
     * 查询角色 分页数据
     */
    @PreAuthorize("role:select")
    @GetMapping("paging")
    public Page<Role> selectRolePage(@RequestParam(required = false, defaultValue = "1") Long page, @RequestParam(required = false, defaultValue = "10") Long size) {
        return this.roleService.selectRolePage(page, size);
    }

    /**
     * 查询角色 列表
     */
    @PreAuthorize("role:select")
    @GetMapping
    public List<Role> selectRoleList(Role role) {
        return this.roleService.selectRoleList(role);
    }

    /**
     * 根据ID集合批量删除角色
     */
    @PreAuthorize("role:delete")
    @DeleteMapping
    public Integer deleteRoles(@RequestBody List<Integer> ids) {
        return this.roleService.deleteRoleByIds(ids);
    }
}
