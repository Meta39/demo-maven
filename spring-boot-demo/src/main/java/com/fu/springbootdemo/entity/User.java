package com.fu.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户
 */
@Data
@TableName("user")
public class User {
    private static final long serialVersionUID = -45656328374893341L;
    @TableId(type = IdType.AUTO,value = "id")
    private Integer id; //id    

    @NotEmpty
    @TableField("username")
    private String username; //用户名    

    @TableField("salt")
    private String salt; //盐    

    @TableField("pwd")
    private String pwd; //密码    

    @NotEmpty
    @TableField("nickname")
    private String nickname; //昵称    

    @NotNull
    @TableField("sex")
    private Integer sex; //性别（0：女 1：男）    

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime; //创建时间    

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime; //更新时间    

    @NotNull
    @TableField("is_ban")
    private Integer isBan; //禁用（0：否 1：是）    

    @TableLogic //逻辑删除
    @TableField("is_delete")
    private Integer isDelete; //删除（0：否 1：是）    

    @TableField("last_login_time")
    private Date lastLoginTime; //最后登录时间    

}
