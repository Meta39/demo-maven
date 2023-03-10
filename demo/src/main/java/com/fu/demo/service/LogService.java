package com.fu.demo.service;

import com.fu.demo.dao.LogDao;
import com.fu.demo.entity.Log;
import com.fu.demo.entity.dto.LogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class LogService {

    @Resource
    private LogDao logDao;

    /**
     * 根据ID查询
     */
    public Log queryById(String tableName, Long id) {
        //不传则默认是当前年月
        if (!StringUtils.hasLength(tableName)) {
            tableName = getYearMonth(0);
        }
        return logDao.queryById(tableName, id);
    }

    /**
     * 查询全部
     */
    public List<Log> findAll(LogDTO logDTO) {
        String tableName = logDTO.getYyyyMM();
        //不传则默认是当前年月
        if (!StringUtils.hasLength(tableName)) {
            tableName = getYearMonth(0);
        }
        return logDao.findAll(tableName, logDTO);
    }

    /**
     * 异步日志入库
     */
    @Async
    public void insert(Log logObj) {
//        log.info("打印当前B线程名称：{}",Thread.currentThread().getName());
        //获取当前年月
        String tableName = getYearMonth(0);
        try {
            logDao.insert(tableName, logObj);
        } catch (BadSqlGrammarException e) {
            log.error("入库异常：", e);
            //如果是不存在表，则创建表
            if (1146 == e.getSQLException().getErrorCode()) {
                //判断下个月的日志表是否存在，如果不存在则创建
                logDao.crateTable(getYearMonth(0));
                //再次入库
                logDao.insert(tableName, logObj);
            }
        }catch (Exception e){
            log.error("未知异常：",e);
        }
    }

    //自定义方法==============================================================================================================
    /**
     * 获取年月
     *
     * @param addOrReduceMonth 正数表示后几个月，负数表示前几个月，默认0，表示当前月
     */
    public static String getYearMonth(int addOrReduceMonth) {
        //获取当前月份的表
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, addOrReduceMonth);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        return dft.format(cal.getTime());
    }

}
