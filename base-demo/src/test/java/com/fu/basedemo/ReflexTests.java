package com.fu.basedemo;

import com.fu.basedemo.reflex.Reflex;
import com.fu.basedemo.reflex.ReflexEnum;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflexTests {

    @Test
    public void test() {
        System.out.println(ReflexEnum.value(ReflexEnum.ReflexA));
    }

    @Test
    void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        //1、获取类信息
        Class<?> clazz = Class.forName("com.fu.basedemo.reflex.Reflex");
        Reflex reflex = (Reflex) clazz.newInstance();
        // 获取指定方法
        Method method = clazz.getMethod("setName", String.class);
        // 调用方法
        method.invoke(reflex, "Meta");
        System.out.println("method调用Reflex.setName()：" + reflex);
    }

}