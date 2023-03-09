package com.fu.springbootdemo.global;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一返回类
 */
public class Res<T> implements Serializable {

    private static final long serialVersionUID = 6558796578827818466L;

    private Integer code; //状态码

    private String msg; //返回消息

    private T data; //数据

    private Res (){}//私有化构造方法

    private static final Res res = new Res();//私有化静态实例化类

    //成功
    public static <T> Res<T> success(T data){
        res.setCode(1);
        res.setMsg("成功");
        res.setData(data);
        return res;
    }

    //一般异常
    public static <T> Res<T> err(String errorMessage){
        res.setCode(0);
        res.setMsg(errorMessage);
        return res;
    }

    //全局捕获异常
    public static <T> Res<T> err(Integer code,String errorMessage){
        res.setCode(code);
        res.setMsg(errorMessage);
        return res;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Res{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Res<?> res = (Res<?>) o;
        return Objects.equals(code, res.code) && Objects.equals(msg, res.msg) && Objects.equals(data, res.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, data);
    }

}