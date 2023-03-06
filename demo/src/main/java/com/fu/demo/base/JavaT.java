package com.fu.demo.base;

import com.fu.demo.mongo.Apple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * java泛型
 * ？ 表示不确定的 java 类型
 * T (type) 表示具体的一个java类型
 * K V (key value) 分别代表java键值中的Key Value
 * E (element) 代表Element
 * 使用：类名<泛型变量名>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class JavaT<T> {
    private T type;

    private void print(){
        //泛型T的使用一般用于对象
        log.info("T为数字类型：{}", new JavaT<>(1));
        log.info("T为数字类型：{}", new JavaT<>(2L));
        log.info("T为String类型：{}", new JavaT<>("A"));
        log.info("T为Apple对象：{}", new JavaT<>(new Apple(1L, "苹果")));

        //泛型K，V的使用一般用于key，value的形式，如Map、HashMap
        Map map = new HashMap<>();
        map.put(1, "A");
        map.put("B", 2);
        map.put("obj", new Apple(2L, "苹果2"));
        log.info("Map<K,V>：{}", map);
    }
}
