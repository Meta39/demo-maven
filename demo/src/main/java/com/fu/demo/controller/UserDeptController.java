package com.fu.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.fu.demo.entity.UserDept;
import com.fu.demo.service.UserDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户关联部门
 */
@RestController
@RequestMapping("userDept")
public class UserDeptController {

    @Resource
    private UserDeptService userDeptService;

    /**
     * 根据ID查询
     *
     * @param id
     */
    @GetMapping("select")
    public UserDept select(@RequestParam Long id) {
        return userDeptService.select(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("selectAll")
    public List<UserDept> selectAll() {
        return userDeptService.selectAll();
    }

    /**
     * 查询全部（分页）
     *
     * @param pageNum  起始页
     * @param pageSize 每页数据量
     */
    @GetMapping("selectPage")
    public PageSerializable<UserDept> selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageSerializable.of(userDeptService.selectAll());
    }

    /**
     * 新增
     *
     * @param userDept
     */
    @PostMapping("insert")
    public Integer insert(@RequestBody @Valid UserDept userDept) {
        return userDeptService.insert(userDept);
    }

    /**
     * 修改
     *
     * @param userDept
     */
    @PostMapping("update")
    public Integer update(@RequestBody @Valid UserDept userDept) {
        return userDeptService.update(userDept);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("delete")
    public Integer delete(@RequestParam Long id) {
        return userDeptService.delete(id);
    }

}

