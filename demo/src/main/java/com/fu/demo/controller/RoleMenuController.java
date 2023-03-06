package com.fu.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.fu.demo.entity.RoleMenu;
import com.fu.demo.service.RoleMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色关联菜单
 */
@RestController
@RequestMapping("roleMenu")
public class RoleMenuController {

    @Resource
    private RoleMenuService roleMenuService;

    /**
     * 根据ID查询
     *
     * @param id
     */
    @GetMapping("select")
    public RoleMenu select(@RequestParam Long id) {
        return roleMenuService.select(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("selectAll")
    public List<RoleMenu> selectAll() {
        return roleMenuService.selectAll();
    }

    /**
     * 查询全部（分页）
     *
     * @param pageNum  起始页
     * @param pageSize 每页数据量
     */
    @GetMapping("selectPage")
    public PageSerializable<RoleMenu> selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageSerializable.of(roleMenuService.selectAll());
    }

    /**
     * 新增
     *
     * @param roleMenu
     */
    @PostMapping("insert")
    public Integer insert(@RequestBody @Valid RoleMenu roleMenu) {
        return roleMenuService.insert(roleMenu);
    }

    /**
     * 修改
     *
     * @param roleMenu
     */
    @PostMapping("update")
    public Integer update(@RequestBody @Valid RoleMenu roleMenu) {
        return roleMenuService.update(roleMenu);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("delete")
    public Integer delete(@RequestParam Long id) {
        return roleMenuService.delete(id);
    }

}

