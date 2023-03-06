package com.fu.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.fu.demo.entity.UserRole;
import com.fu.demo.service.UserRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户关联角色
 */
@RestController
@RequestMapping("userRole")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    /**
     * 根据ID查询
     *
     * @param id
     */
    @GetMapping("select")
    public UserRole select(@RequestParam Long id) {
        return userRoleService.select(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("selectAll")
    public List<UserRole> selectAll() {
        return userRoleService.selectAll();
    }

    /**
     * 查询全部（分页）
     *
     * @param pageNum  起始页
     * @param pageSize 每页数据量
     */
    @GetMapping("selectPage")
    public PageSerializable<UserRole> selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageSerializable.of(userRoleService.selectAll());
    }

    /**
     * 新增
     *
     * @param userRole
     */
    @PostMapping("insert")
    public Integer insert(@RequestBody @Valid UserRole userRole) {
        return userRoleService.insert(userRole);
    }

    /**
     * 修改
     *
     * @param userRole
     */
    @PostMapping("update")
    public Integer update(@RequestBody @Valid UserRole userRole) {
        return userRoleService.update(userRole);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("delete")
    public Integer delete(@RequestParam Long id) {
        return userRoleService.delete(id);
    }

}

