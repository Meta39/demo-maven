package com.fu.springbootdynamicdatasource.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId
    private Integer id;
    private String name;
}
