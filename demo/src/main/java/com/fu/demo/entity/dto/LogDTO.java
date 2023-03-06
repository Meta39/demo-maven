package com.fu.demo.entity.dto;

import lombok.Data;

@Data
public class LogDTO {
    /**
     * 年月
     */
    private String yyyyMM;
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
     * 详细错误信息
     */
    private String errorMsg;
    /**
     * 当月时间段-开始时间
     */
    private String startTime;
    /**
     * 当月时间段-结束时间
     */
    private String endTime;
}
