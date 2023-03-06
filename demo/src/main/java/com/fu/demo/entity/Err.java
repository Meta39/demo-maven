package com.fu.demo.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 自定义异常类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Err extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 7558796578827818466L;
    private int code = 500;//默认错误状态码为500
    private String msg;//自定义错误信息

    /**
     * 默认普通异常
     * @param msg 错误信息
     */
    public Err(String msg){
        this.msg = msg;
    }

}