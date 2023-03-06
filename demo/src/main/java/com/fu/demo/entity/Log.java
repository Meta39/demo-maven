package com.fu.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * 日志实体类
 */
@Data
public class Log {
    /**
     * ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求URI
     */
    private String requestURI;
    /**
     * 请求数据
     */
    private String requestData;
    /**
     * 返回数据
     */
    private String respondData;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 详细错误信息
     */
    private String errorMsg;
    /**
     * 创建时间
     */
    private Date createTime;
}
