package com.fu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.fu.demo.entity.Menu;
import com.fu.demo.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 菜单（运维谨慎用超级用户去新增、修改、删除）
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 根据ID查询
     *
     * @param id
     */
    @GetMapping("select")
    public Menu select(@RequestParam Long id) {
        return menuService.select(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("selectAll")
    public List<Menu> selectAll() {
        return menuService.selectAll();
    }

    /**
     * 查询全部（分页）
     *
     * @param pageNum  起始页
     * @param pageSize 每页数据量
     */
    @GetMapping("selectPage")
    public PageSerializable<Menu> selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageSerializable.of(new ArrayList<>(menuService.selectAll()));
    }

    /**
     * 新增
     *
     * @param menu
     */
    @PostMapping("insert")
    public Integer insert(@RequestBody @Valid Menu menu) {
        return menuService.insert(menu);
    }

    /**
     * 修改
     *
     * @param menu
     */
    @PostMapping("update")
    public Integer update(@RequestBody @Valid Menu menu) {
        return menuService.update(menu);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("delete")
    public Integer delete(@RequestParam Long id) {
        return menuService.delete(id);
    }

    /**
     * 查询全部菜单树状结构（推荐）
     */
    @GetMapping("menuTree")
    public List<Menu> menuTree() {
        return menuService.menuTree();
    }

    /**
     * 递归查询全部菜单树状结构（不推荐）
     */
    @GetMapping("menuTree2")
    public Set<Menu> menuTree2(){
        return menuService.menuTree2();
    }
}

