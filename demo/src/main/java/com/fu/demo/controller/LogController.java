package com.fu.demo.controller;

import com.fu.demo.entity.Log;
import com.fu.demo.entity.dto.LogDTO;
import com.fu.demo.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 日志
 */
@RestController
@RequestMapping("log")
public class LogController {

    @Resource
    private LogService logService;

    /**
     * 根据年月和ID查询
     *
     * @param yyyyMM 年月
     * @param id     ID
     */
    @GetMapping("queryById")
    public Log queryById(@RequestParam(required = false) String yyyyMM, @RequestParam Long id) {
        return logService.queryById(yyyyMM,id);
    }

    /**
     * 分页查询
     */
    @PostMapping("findAll")
    public List<Log> findAll(@RequestBody LogDTO logDTO) {
        return logService.findAll(logDTO);
    }

}
