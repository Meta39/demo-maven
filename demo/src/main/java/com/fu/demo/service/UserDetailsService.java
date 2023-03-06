package com.fu.demo.service;

import com.fu.demo.entity.UserDetails;
import com.fu.demo.dao.UserDetailsDao;

import javax.annotation.Resource;

import com.fu.demo.entity.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService {
    @Resource
    private UserDetailsDao userDetailsDao;

    /**
     * 根据ID查询
     */
    public UserDetails queryById(Long id) {
        return userDetailsDao.queryById(id);
    }

    /**
     * 查询全部
     */
    public List<UserDetails> findAll() {
        return userDetailsDao.findAll();
    }

    /**
     * 新增
     */
    public Integer insert(UserDetails userDetails) {
        return userDetailsDao.insert(userDetails);
    }

    /**
     * 更新
     */
    public Integer update(UserDetails userDetails) {
        return userDetailsDao.update(userDetails);
    }

    /**
     * 删除
     */
    public Integer deleteById(Long id) {
        return userDetailsDao.deleteById(id);
    }

    /**
     * 通过用户ID查询用户详细信息
     * @param userId 用户id
     * @return
     */
    public UserVO queryByUserId(Long userId) {
        return userDetailsDao.queryByUserId(userId);
    }
}
