package com.fu.demo.entity;

import java.util.Date;

import lombok.Data;

@Data
public class UserDetails {
    private static final long serialVersionUID = -54115220271193747L;
    /**
     * ID
     */
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 公司
     */
    private String company;

    /**
     * 职位
     */
    private String position;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
