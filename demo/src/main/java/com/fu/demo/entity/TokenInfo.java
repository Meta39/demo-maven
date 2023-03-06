package com.fu.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String token;//前端请求时的token
    private Long userId;//用户ID
    private String userName;//用户名
    private List<Dept> depts;//部门组
    private List<Role> roles;//角色组
    private Set<Menu> menus;//菜单树
    private Set<String> empowers;//权限
}
