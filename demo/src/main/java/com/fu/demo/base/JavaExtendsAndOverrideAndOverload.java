package com.fu.demo.base;

import lombok.extern.slf4j.Slf4j;

/**
 * 继承、重写、重载
 */
@Slf4j
public class JavaExtendsAndOverrideAndOverload {
    private String name;

    public void overLoad(){
        log.info("没被重载");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * 继承父类方法，重写/重载里面的方法
 */
@Slf4j
class JavaObjExtends extends JavaExtendsAndOverrideAndOverload{

    /**
     * 重写：改变父类的setName方法，方法名和变量必须和父类相同
     * @return
     */
    @Override
    public String getName() {
        JavaObjExtends javaObjExtends = new JavaObjExtends();
        javaObjExtends.setName("被重写以后的名称");
        //调用没有被重载的方法
        super.overLoad();
        //调用重载的方法
        this.overLoad("Meta39");
        return javaObjExtends.getName();
    }

    /**
     * 重载：方法名相同，参数必须不同
     * @param name 姓名
     */
    public void overLoad(String name) {
        log.info("重载，参数必须不相同：{}",name);
    }
}
