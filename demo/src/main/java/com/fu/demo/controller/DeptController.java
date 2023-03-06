package com.fu.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.fu.demo.entity.Dept;
import com.fu.demo.service.DeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 部门
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     * 根据ID查询
     *
     * @param id
     */
    @GetMapping("select")
    public Dept select(@RequestParam Long id) {
        return deptService.select(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("selectAll")
    public List<Dept> selectAll() {
        return deptService.selectAll();
    }

    /**
     * 查询全部（分页）
     *
     * @param pageNum  起始页
     * @param pageSize 每页数据量
     */
    @GetMapping("selectPage")
    public PageSerializable<Dept> selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageSerializable.of(deptService.selectAll());
    }

    /**
     * 新增
     *
     * @param dept
     */
    @PostMapping("insert")
    public Integer insert(@RequestBody @Valid Dept dept) {
        return deptService.insert(dept);
    }

    /**
     * 修改
     *
     * @param dept
     */
    @PostMapping("update")
    public Integer update(@RequestBody @Valid Dept dept) {
        return deptService.update(dept);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("delete")
    public Integer delete(@RequestParam Long id) {
        return deptService.delete(id);
    }

}

