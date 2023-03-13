package com.fu.springbootdemo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fu.springbootdemo.annotation.PreAuthorize;
import com.fu.springbootdemo.entity.User;
import com.fu.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据ID查询用户
     *
     * @param id ID
     */
    @PreAuthorize("user:select")
    @GetMapping("{id}")
    public User selectUserById(@PathVariable("id") Integer id) {
        return this.userService.selectUserById(id);
    }

    /**
     * 新增用户
     *
     * @param user 用户实体类
     */
    @PreAuthorize("user:insert")
    @PostMapping
    public Integer insertUser(@RequestBody @Valid User user) {
        return this.userService.insertUser(user);
    }

    /**
     * 更新用户
     *
     * @param user 用户实体类
     */
    @PreAuthorize("user:update")
    @PutMapping
    public Integer updateUser(@RequestBody @Valid User user) {
        return this.userService.updateUser(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param id ID
     */
    @PreAuthorize("user:delete")
    @DeleteMapping("{id}")
    public Integer deleteUser(@PathVariable Integer id) {
        return this.userService.deleteUserById(id);
    }

    /**
     * 查询用户 分页数据
     */
    @PreAuthorize("user:select")
    @GetMapping("paging")
    public Page<User> selectUserPage(@RequestParam(required = false, defaultValue = "1") Long page, @RequestParam(required = false, defaultValue = "10") Long size) {
        return this.userService.selectUserPage(page, size);
    }

    /**
     * 查询用户 列表
     */
    @PreAuthorize("user:select")
    @GetMapping
    public List<User> selectUserList(User user) {
        return this.userService.selectUserList(user);
    }

    /**
     * 根据ID集合批量删除用户
     */
    @PreAuthorize("user:delete")
    @DeleteMapping
    public Integer deleteUsers(@RequestBody List<Integer> ids) {
        return this.userService.deleteUserByIds(ids);
    }

    /**
     * 根据用户ID查询用户的角色集合
     * @param userId 用户ID
     */
    @PreAuthorize("user:select")
    @GetMapping("selectUserRole/{userId}")
    public User selectUserRole(@PathVariable Integer userId){
        return this.userService.selectUserRole(userId);
    }
}
