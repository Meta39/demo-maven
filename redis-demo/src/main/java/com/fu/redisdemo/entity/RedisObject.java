package com.fu.redisdemo.entity;

import lombok.Data;

import java.util.List;

@Data
public class RedisObject {
    private Integer id;
    private List<String> username;
}
