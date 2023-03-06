package com.fu.demo.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept implements Serializable {
    private Long id;//部门ID    
    private Long pId;//父部门ID    
    private String name;//部门名称    
    private String remark;//备注    
    private Long creator;//创建者    
    private Date createTime;//创建时间    
}

