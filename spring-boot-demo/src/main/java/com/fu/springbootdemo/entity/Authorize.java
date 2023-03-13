package com.fu.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 权限
 */
@Data
@TableName("authorize")
public class Authorize implements Serializable {
    private static final long serialVersionUID = -47338307442719018L;
    @TableId(type = IdType.AUTO,value = "id")
    private Integer id; //id

    @TableField("p_id")
    private Integer pId; //pId

    @NotEmpty
    @TableField("authorize_name")
    private String authorizeName; //权限名称（后端授权）    

    @NotEmpty
    @TableField("authorize_name_cn")
    private String authorizeNameCn; //权限名称中文（前端展示）    

}
