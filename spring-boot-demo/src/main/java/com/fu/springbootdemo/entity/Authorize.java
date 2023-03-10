package com.fu.springbootdemo.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("authorize")
public class Authorize {
    private static final long serialVersionUID = -47338307442719018L;
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Integer id; //id    

    @TableField("authorize_name")
    private String authorizeName; //权限名称（后端授权）    

    @TableField("authorize_name_cn")
    private String authorizeNameCn; //权限名称中文（前端展示）    

}
