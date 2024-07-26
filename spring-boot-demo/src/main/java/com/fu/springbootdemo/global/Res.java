package com.fu.springbootdemo.global;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一返回类
 */
@SuppressWarnings("unchecked")
public class Res<T> implements Serializable {

    private static final long serialVersionUID = 6558796578827818466L;

    private Integer code; //状态码

    private String msg; //返回消息

    private T data; //数据

    private Res() {
    }//私有化构造方法

    private static final Res res = new Res<>();//私有化静态实例化类

    /**
     * 成功（前端没有特别要求状态码，直接用这个。）
     *
     * @param data 数据
     */
    public static <T> Res<T> ok(T data) {
        return ok("success", data);
    }

    /**
     * 成功（注意：前端要求返回信息要具体的才用这个，否则不使用！）
     *
     * @param message 成功信息（注意：前端要求返回信息要具体的才用这个，否则不使用！）
     * @param data    数据
     */
    public static <T> Res<T> ok(String message, T data) {
        res.setCode(1);
        res.setMsg(message);
        res.setData(data);
        return res;
    }

    /**
     * 一般异常（注意：前端没有特别要求状态码，直接用这个。）
     *
     * @param errorMessage 异常信息
     */
    public static <T> Res<T> err(String errorMessage) {
        return err(0, errorMessage);
    }

    /**
     * 全局捕获异常（注意：前端要求状态码为其它的才使用这个，否则不使用。）
     *
     * @param code         状态码（注意：前端无特殊要求不要使用这个！就算要使用也要在Code枚举类里枚举出来，方便到时候统一修改！）
     * @param errorMessage 错误信息
     */
    public static <T> Res<T> err(Integer code, String errorMessage) {
        res.setCode(code);
        res.setMsg(errorMessage);
        res.setData(null);//设置为null防止上次数据没有清空
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