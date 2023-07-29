package com.fu.redisdemo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class NodesReplaceAllTest {

    @Test
    public void test() {
        List<String> list = Arrays.asList("a", "b", "c");
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            str = str.replaceAll(str, "redis://" + str);
            list.set(i, str);
        }
        list.forEach(System.out::println);
//        String str = "abc";
//        System.out.println(str.replaceAll("a","b"));
    }

}
