package com.fu.demo.base;

import com.fu.demo.entity.Err;
import com.fu.demo.mongo.Apple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Objects;

/**
 * ==和equals的区别
 */
@Slf4j
public class JavaEqualsHashCode {

    /**
     * ==号与equals的区别:
     * 对于基本类型，== 判断两个值是否相等，基本类型没有 equals() 方法。
     * 对于引用类型，== 判断两个变量是否引用同一个对象，而 equals() 判断引用的对象是否等价。
     * 如：Err未重写equals和hashCode方法，因此equals输出是false，Hash值不一致HashSet长度是2。
     * 如：Apple使用@Data注解，重写equals和hashCode方法，因此输出equals是true，由于HashSet无序，不允许重复数据的特性，添加到HashSet再输出长度是1，证明了2个新建的对象的Hash值也是一致的。
     */
    @Test
    public void equalsEntity(){
        //Object 的 equals 方法容易抛空指针异常，应使用常量或确定有值的对象来调用 equals。推荐使用 JDK7 引入的工具类 java.util.Objects#equals(Object a, Object b)
        boolean equals = Objects.equals("object1", "object2");
        //JavaNotEqualsAndHashCodeObject未重写equals和hashCode方法
        JavaNotEqualsAndHashCodeObject notEqualsAndHashCodeObject1 = new JavaNotEqualsAndHashCodeObject("name");
        JavaNotEqualsAndHashCodeObject notEqualsAndHashCodeObject2 = new JavaNotEqualsAndHashCodeObject("name");
        //不确定2个对象是否为null的情况下建议用Objects.equals。
        log.info("未重写equals和hashCode方法。两个对象比较结果为：{}",Objects.equals(notEqualsAndHashCodeObject1,notEqualsAndHashCodeObject2));
        HashSet<JavaNotEqualsAndHashCodeObject> notEqualsAndHashCodeObjectHashSet = new HashSet<>(2);
        notEqualsAndHashCodeObjectHashSet.add(notEqualsAndHashCodeObject1);
        notEqualsAndHashCodeObjectHashSet.add(notEqualsAndHashCodeObject2);
        log.info("未重写equals和hashCode方法，HashSet相同内容的对象长度为：{}",notEqualsAndHashCodeObjectHashSet.size());

        log.info("======================");

        //Apple使用@Data重新equals和hashCode
        Apple apple1 =new Apple(1L,"apple");
        Apple apple2 =new Apple(1L,"apple");
        log.info("重写equals和hashCode方法。两个对象比较结果为：{}",Objects.equals(apple1,apple2));
        HashSet<Apple> hashSet = new HashSet<>(2);
        hashSet.add(apple1);
        hashSet.add(apple2);
        log.info("重写equals和hashCode方法，HashSet相同内容的对象长度为：{}",hashSet.size());
    }
}
