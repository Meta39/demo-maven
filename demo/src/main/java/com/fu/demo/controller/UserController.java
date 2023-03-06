package com.fu.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.fu.demo.entity.User;
import com.fu.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户表
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 根据ID查询
     *
     * @param id
     */
    @GetMapping("select")
    public User select(@RequestParam Long id) {
        return userService.select(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("selectAll")
    public List<User> selectAll() {
        return userService.selectAll();
    }

    /**
     * 查询全部（分页）
     *
     * @param pageNum  起始页
     * @param pageSize 每页数据量
     */
    @GetMapping("selectPage")
    public PageSerializable<User> selectPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageSerializable.of(userService.selectAll());
    }

    /**
     * 新增
     *
     * @param user
     */
    @PostMapping("insert")
    public Integer insert(@RequestBody @Valid User user) {
        return userService.insert(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    @PostMapping("update")
    public Integer update(@RequestBody @Valid User user) {
        return userService.update(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("delete")
    public Integer delete(@RequestParam Long id) {
        return userService.delete(id);
    }

}

