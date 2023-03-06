package com.fu.demo.filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.demo.entity.Log;
import com.fu.demo.entity.Res;
import com.fu.demo.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 异步日志入库
 * 高并发情况下：
 * 1. implements ApplicationRunner接口重写run()。启动时就执行一次创建表语句CREATE TABLE IF NOT EXISTS ......
 * 2. @Async异步方法：
 *     @Async("logAsync")
 *     public void logAsync(Log logObj) throws InterruptedException {
 *         LogTask.logQueue.offer(logObj, LogTask.TIME_OUT, TimeUnit.SECONDS);
 *     }
 *
 * 3. 定时任务类初始化当前月份变量private static String NOW_DATE = new SimpleDateFormat("yyyyMM").format(System.currentTimeMillis());
 * 定时任务类LogTask代码如下：
 *     public static LinkedBlockingQueue<Log> logQueue = new LinkedBlockingQueue<>();//日志队列
 *     public static final int TIME_OUT = 10;//队列写入内容超时时间
 *     private static final int ARRAY_LIST_SIZE = 16;//初始化队列长度
 *     private static String NOW_DATE = new SimpleDateFormat("yyyyMM").format(System.currentTimeMillis());//当前时间用于判断入那个表
 *
 *     @Scheduled(fixedRate=1000)
 *     private void configureTasks(){
 *         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//SimpleDateFormat该类线程不安全
 *         //获取当前年月
 *         if (null != logQueue.peek()){
 *             String tableName = sdf.format(logQueue.peek().getCreateTime());
 *             if (NOW_DATE.equals(tableName)) {//判断当前时间和表名是否相等
 *                 //把日志对象添加到Queue队列
 *                 List<Log> logList = new ArrayList<>(ARRAY_LIST_SIZE);
 *                 logQueue.drainTo(logList);//取出所有队列里的内容
 *                 logDao.insertBatch(tableName, logList);//批量日志入库
 *             } else {
 *                 //NOW_DATE防止运行过程中跨月了，但是没有重启过程序，NOW_DATE变成了上个月，也只会执行一次创表语句。
 *                 logDao.crateTable(tableName);
 *                 NOW_DATE = tableName;
 *             }
 *         }
 *     }
 */
@Slf4j
//@Component
//@WebFilter(urlPatterns = "/*", filterName = "logFilter")
public class LogFilter implements Filter {
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${log-application.ignore-uri}")
    private String ignoreURIs;
    @Resource
    private LogService logService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = new HashMap<>();
        if (parameterMap != null) {
            parameterMap.forEach((key, value) -> {
                map.put(key, value[0]);
            });
        }

        LogHttpServletResponseWrapper logHttpServletResponseWrapper = new LogHttpServletResponseWrapper(response);//获取返回内容
        filterChain.doFilter(request, logHttpServletResponseWrapper);//必须放到logHttpServletResponseWrapper.getContent()前面，否则拿不到数据

        //异步写入日志入库，用try catch报错就报错，不用管
        try {
            String content = new String(logHttpServletResponseWrapper.getContent(), StandardCharsets.UTF_8);
//            log.info("原返回值:{}",content);
            ObjectMapper om = new ObjectMapper();
            Res r = om.readValue(content, Res.class);

//            log.info(ignoreURI);
            //URI白名单
            boolean haveIgnoreURI = Arrays.stream(ignoreURIs.split(",")).anyMatch(ignoreURI -> request.getRequestURI().contains(ignoreURI));
            if (!haveIgnoreURI) {
                Log logObj = new Log();
//            logObj.setId(UUID.randomUUID().toString());
                logObj.setUserId("1");
                logObj.setUsername("Meta");
                logObj.setApplicationName(applicationName);
                logObj.setMethod(request.getMethod());
                logObj.setRequestURI(request.getRequestURI());
                logObj.setRequestData(String.valueOf(map));
                logObj.setCode(r.getCode());
                logObj.setRespondData(om.writeValueAsString(r.getData()));
                logObj.setMsg(r.getMsg());
                logObj.setErrorMsg(om.writeValueAsString(r.getDetailErrorMsg()));
                logObj.setCreateTime(new Date());
//                log.info("打印当前A线程名称：{}", Thread.currentThread().getName());
                logService.insert(logObj);
                r.setDetailErrorMsg(null);//获取完以后，设置详细异常为null
            }
            String newContent = om.writeValueAsString(r);

            //修改返回内容长度，解决返回内容长度不一致导致请求卡住的问题
            response.setContentLength(newContent.getBytes(StandardCharsets.UTF_8).length);//这里要注意：setContentLength(字符串的字节长度，不是字符串的长度)
            //修改完写入输出流，返回给前端
            try (ServletOutputStream out = response.getOutputStream()) {//流异常自动关闭流
                out.write(newContent.getBytes(StandardCharsets.UTF_8));//写入返回内容
                out.flush();//刷新
            } catch (Exception e) {
                log.error("修改返回内容后ServletOutputStream流处理异常：", e);
            }
        }catch (JsonParseException jsonParseException){
            //如果是JSON转换异常，那就不进行转换，返回原返回对象。
            log.error("JSON转换异常，返回原类型：",jsonParseException);
            String content = new String(logHttpServletResponseWrapper.getContent(), StandardCharsets.UTF_8);
            response.setContentLength(content.getBytes(StandardCharsets.UTF_8).length);
            try (ServletOutputStream out = response.getOutputStream()){//流异常自动关闭流
                out.write(content.getBytes(StandardCharsets.UTF_8));//写入返回内容
                out.flush();//刷新
            }catch (Exception e){
                log.error("原返回内容ServletOutputStream流处理异常：", e);
            }
        }catch (Exception e) {
            log.error("未知异常：", e);
        }
    }
}
