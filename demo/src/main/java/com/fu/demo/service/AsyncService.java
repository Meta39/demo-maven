package com.fu.demo.service;

import com.fu.demo.dao.DeptDao;
import com.fu.demo.dao.RoleDao;
import com.fu.demo.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AsyncService {
    @Resource
    private UserDao userDao;
    @Resource
    private DeptDao deptDao;
    @Resource
    private RoleDao roleDao;

    @Async("demoAsync")
    public void selectByAsync(){
        long startTime = System.currentTimeMillis();
        log.info("开始时间：{}",startTime);
        userDao.select(1L);
        userDao.select(2L);
        deptDao.select(1L);
        deptDao.select(2L);
        roleDao.select(1L);
        roleDao.select(2L);
        long endTime = System.currentTimeMillis();
        log.info("结束时间：{}",endTime);
        log.info("耗时：{}",endTime - startTime);
    }
}
