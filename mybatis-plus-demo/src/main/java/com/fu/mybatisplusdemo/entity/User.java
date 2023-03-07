package com.fu.mybatisplusdemo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fu.mybatisplusdemo.enums.SexEnum;
import lombok.Data;

@Data
@TableName("user")
public class User {

    private static final long serialVersionUID = 766730990998679444L;

    @TableId(type = IdType.AUTO,value = "id")
    private Integer id; //主键ID

    @TableField("name")
    private String name; //用户名

    @TableField("sex")
    private SexEnum sex; //性别（0：女 1：男）用枚举类型存储

    @TableField("nick_name")
    private String nickName; //昵称

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime; //创建时间

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime; //修改时间

    //物理删除SQL语句为：DELETE FROM user WHERE id=?
    //设置@TableLogic逻辑删除注解SQL语句为：UPDATE user SET is_deleted=1 WHERE id=? AND is_deleted=0
    @TableLogic//逻辑删除标识
    @TableField("is_delete")
    private int isDeleted; //逻辑删除（0：否 1：是）
}
