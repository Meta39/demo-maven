package com.fu.demo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Document("apple") //apple是集合名称建议全部小写并且和实体类名一致
@NoArgsConstructor
@AllArgsConstructor
public class Apple implements Serializable {

    //jdk8版本强烈建议需要序列化的类加上此serialVersionUID变量，可由idea生成
    private static final long serialVersionUID = 3984134152513452652L;
    @NotNull(message = "ID不能为空")
    private Long id;
    @NotBlank(message = "名称不能为空")
    private String name;
}
