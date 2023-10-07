package com.fu.springbootdemo.global;

import java.io.Serializable;
import java.util.Objects;

/**
 * 自定义异常返回类
 */
public class Err extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 6558796578827818467L;

    private Integer code; //错误码

    private String msg; //错误信息

    private Err(){}//私有化构造方法

    private static final Err err = new Err();

    /**
     * 只返回异常信息给前端(推荐)
     * 默认错误状态码为：0
     * @param msg 错误信息
     */
    public static Err msg(String msg){
        err.setCode(0);
        err.setMsg(msg);
        return err;
    }

    /**
     * 全局异常捕获填写状态码和异常信息返回给前端（一般情况下不推荐，只有当前端要求通过code判断时才使用。就算要使用也要在Code枚举类里枚举出来，方便到时候统一修改！）
     * @param code 错误枚举类
     */
    public static Err codeAndMsg(Code code){
        err.setCode(code.getErrCode());
        err.setMsg(code.getErrMessage());
        return err;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Err{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Err err = (Err) o;
        return Objects.equals(code, err.code) && Objects.equals(msg, err.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg);
    }

}
