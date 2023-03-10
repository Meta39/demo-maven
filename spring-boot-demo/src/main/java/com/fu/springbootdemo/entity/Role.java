package com.fu.springbootdemo.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("role")
public class Role {
    private static final long serialVersionUID = -38372391020893738L;
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Integer id; //id    

    @TableField("role_name")
    private String roleName; //角色名称    

}
