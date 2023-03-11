package com.fu.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 角色
 */
@Data
@TableName("role")
public class Role {
    private static final long serialVersionUID = -38372391020893738L;
    @TableId(type = IdType.AUTO,value = "id")
    private Integer id; //id    

    @NotEmpty
    @TableField("role_name")
    private String roleName; //角色名称

    @NotNull
    @TableField("is_ban")
    private Boolean isBan; //禁用角色

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime; //创建时间

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime; //更新时间

}
