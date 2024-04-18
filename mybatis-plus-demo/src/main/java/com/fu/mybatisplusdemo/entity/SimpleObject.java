package com.fu.mybatisplusdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("simple_object")
public class SimpleObject {
    private Integer id;
    private String name;
}
