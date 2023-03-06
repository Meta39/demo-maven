package com.fu.demo.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu implements Serializable {
    private Long id;//角色菜单ID    
    private Long roleId;//角色ID    
    private Long menuId;//菜单ID    
}

