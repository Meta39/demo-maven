package com.fu.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "elastic")//indexName索引名称等价于MySQL的表
public class ElasticSearchEntity {
    @Id
    @Field(type = FieldType.Keyword)//主键
    private String id;

    @Field(type = FieldType.Text)//字符串对应文本类型
    private String name;

    @Field(type = FieldType.Date)//日期类型
    private Date createTime;
}
