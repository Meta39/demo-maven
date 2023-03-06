package com.fu.demo.controller;

import com.fu.demo.entity.vo.UserVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.fu.demo.entity.UserDetails;
import com.fu.demo.service.UserDetailsService;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户详细信息表
 */
@RestController
@RequestMapping("userDetails")
public class UserDetailsController {

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 根据ID查询
     *
     * @param id ID
     */
    @GetMapping("queryById")
    public UserDetails queryById(@RequestParam Long id) {
        return userDetailsService.queryById(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("findAll")
    public List<UserDetails> findAll() {
        return userDetailsService.findAll();
    }

    /**
     * 查询全部（分页）
     *
     * @param pageNum  起始页
     * @param pageSize 每页数据量
     */
    @GetMapping("findPage")
    public PageSerializable<UserDetails> findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageSerializable.of(userDetailsService.findAll());
    }

    /**
     * 新增
     *
     * @param userDetails
     */
    @PostMapping("insert")
    public Integer insert(@RequestBody @Valid UserDetails userDetails) {
        return userDetailsService.insert(userDetails);
    }

    /**
     * 修改
     *
     * @param userDetails
     */
    @PostMapping("update")
    public Integer update(@RequestBody @Valid UserDetails userDetails) {
        return userDetailsService.update(userDetails);
    }

    /**
     * 删除
     *
     * @param id ID
     */
    @PostMapping("deleteById")
    public Integer deleteById(@RequestParam Long id) {
        return userDetailsService.deleteById(id);
    }

    /**
     * 联表查询
     * 通过用户ID查询用户详细信息
     * @param userId 用户ID
     * @return
     */
    @GetMapping("queryByUserId")
    public UserVO queryByUserId(@RequestParam Long userId){
        return userDetailsService.queryByUserId(userId);
    }

}
