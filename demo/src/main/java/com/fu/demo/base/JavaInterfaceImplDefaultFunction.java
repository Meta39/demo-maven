package com.fu.demo.base;

import org.junit.jupiter.api.Test;

/**
 * 调用接口中的默认方法
 */
public class JavaInterfaceImplDefaultFunction {

    @Test
    public void interfaceImplDefaultFunction(){
        JavaInterface javaInterface = new JavaInterfaceImpl();
        //调用接口中的print()默认方法
        javaInterface.print();
        //调用实现接口的方法
        javaInterface.needToImpl();
    }

}
