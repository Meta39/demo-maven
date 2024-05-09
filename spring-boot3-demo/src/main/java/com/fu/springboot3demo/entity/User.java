package com.fu.springboot3demo.entity;

import com.fu.springboot3demo.annotations.Desensitize;
import com.fu.springboot3demo.enums.Desensitization;
import lombok.Data;

@Data
public class User {

    private Long id;//ID

    @Desensitize(Desensitization.USERNAME)//姓名脱敏
    private String name;//姓名

}
