package com.fu.demo.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDept implements Serializable {
    private Long id;//用户关联部门ID    
    private Long userId;//用户ID    
    private Long deptId;//部门ID    
}

