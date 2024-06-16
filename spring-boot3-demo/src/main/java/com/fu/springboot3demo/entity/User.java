package com.fu.springboot3demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fu.springboot3demo.annotations.Desensitize;
import com.fu.springboot3demo.enums.Desensitization;
import lombok.Data;

@Data
@TableName("user")
public class User {

    @TableId(type = IdType.INPUT)
    private Long id;//ID

    @Desensitize(Desensitization.USERNAME)//姓名脱敏
    private String name;//姓名

}
