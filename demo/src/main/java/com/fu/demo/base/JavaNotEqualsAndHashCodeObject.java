package com.fu.demo.base;

/**
 * 不重写Equals和HashCode方法的类
 */
public class JavaNotEqualsAndHashCodeObject {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JavaNotEqualsAndHashCodeObject(String name) {
        this.name = name;
    }
}
