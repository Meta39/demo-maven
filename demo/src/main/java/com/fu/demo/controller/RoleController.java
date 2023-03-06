package com.fu.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.fu.demo.entity.Role;
import com.fu.demo.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 根据ID查询
     *
     * @param id
     */
    @GetMapping("select")
    public Role select(@RequestParam Long id) {
        return roleService.select(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("selectAll")
    public List<Role> selectAll() {
        return roleService.selectAll();
    }

    /**
     * 查询全部（分页）
     *
     * @param pageNum  起始页
     * @param pageSize 每页数据量
     */
    @GetMapping("selectPage")
    public PageSerializable<Role> selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageSerializable.of(roleService.selectAll());
    }

    /**
     * 新增
     *
     * @param role
     */
    @PostMapping("insert")
    public Integer insert(@RequestBody @Valid Role role) {
        return roleService.insert(role);
    }

    /**
     * 修改
     *
     * @param role
     */
    @PostMapping("update")
    public Integer update(@RequestBody @Valid Role role) {
        return roleService.update(role);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("delete")
    public Integer delete(@RequestParam Long id) {
        return roleService.delete(id);
    }

}

