package com.fu.springbootdemo.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("user")
public class User {
    private static final long serialVersionUID = -45656328374893341L;
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Integer id; //id    

    @TableField("username")
    private String username; //用户名    

    @TableField("salt")
    private String salt; //盐    

    @TableField("pwd")
    private String pwd; //密码    

    @TableField("nickname")
    private String nickname; //昵称    

    @TableField("sex")
    private Integer sex; //性别（0：女 1：男）    

    @TableField("create_time")
    private Date createTime; //创建时间    

    @TableField("update_time")
    private Date updateTime; //更新时间    

    @TableField("is_ban")
    private Integer isBan; //禁用（0：否 1：是）    

    @TableField("is_delete")
    private Integer isDelete; //删除（0：否 1：是）    

    @TableField("last_login_time")
    private Date lastLoginTime; //最后登录时间    

}
