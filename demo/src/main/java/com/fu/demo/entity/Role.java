package com.fu.demo.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private Long id;//角色ID    
    private String name;//角色名称    
    private String remark;//备注    
    private Boolean disabled;//禁用：0否，1是    
    private Long creator;//创建人ID    
    private Date createTime;//创建时间    
}

