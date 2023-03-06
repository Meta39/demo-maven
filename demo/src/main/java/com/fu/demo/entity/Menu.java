package com.fu.demo.entity;

import java.util.Date;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {
    private Long id;//菜单ID    
    private Long pId;//父菜单ID，存储导航栏层级关系    
    private String name;//菜单名称，与 Vue 路由中的 name 属性对应    
    private String nameZh;//菜单中文名称，用于渲染导航栏（菜单）界面    
    private String path;//与 Vue 路由中的 path 对应，即地址路径    
    private String icon;//菜单icon，element 图标类名，用于渲染菜单名称前的小图标    
    private String component;//组件名，用于解析路由对应的组件    
    private String remark;//备注    
    private Long creator;//创建人ID    
    private Date createTime;//创建时间
    private List<Menu> childList;//子列表
    private Set<Menu> childList2;//递归子列表
}

