package com.fu.demo.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
    private Long id;//用户角色ID    
    private Long userId;//用户ID    
    private Long roleId;//角色ID    
}

