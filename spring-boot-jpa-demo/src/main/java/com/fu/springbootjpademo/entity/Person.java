package com.fu.springbootjpademo.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * 人员
 */
@Data
@Entity
@Table(appliesTo = "person",comment = "人员表")//appliesTo指表的名称（必填），MySQL表名小写，用Person会抛异常。
public class Person {
    @Id//主键标识
    @GeneratedValue(strategy = GenerationType.IDENTITY)//数据库主键策略，默认是AUTO。
    @Comment("主键ID")
    private Long id;

    @Column(name = "name",length = 20,unique = true,nullable = false)//字段属性设置
    @Comment("名称")//字段备注
    private String name;

    @Comment("性别")
    private Integer sex;

    @Transient//此注解表示数据库不创建该字段
    private String ignore;//忽略字段

}
