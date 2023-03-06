package com.fu.demo.entity;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res<T> implements Serializable {
    private static final long serialVersionUID = 6558796578827818466L;
    /**
     * 状态码
     */
    private int code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    /**
     * 详细错误信息
     */
    private T detailErrorMsg;

    public static <T> Res<T> ok(T data) {
        return returnRes( 0, "success",data);
    }

    public static <T> Res<T> err(String msg) {
        return returnRes(Status.FAIL.getStatus(),  msg,null);
    }
    public static <T> Res<T> err(int code, String msg) {
        return returnRes( code, msg,null);
    }
    private static <T> Res<T> returnRes( int code, String msg,T data) {
        Res<T> res = new Res<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }
    //==============================日志入库详细异常信息==========================================
    public static <T> Res<T> err(String msg,T detailErrorMsg) {
        return returnRes(Status.FAIL.getStatus(),msg, null ,detailErrorMsg);
    }
    public static <T> Res<T> err(int code, String msg,T detailErrorMsg) {
        return returnRes( code, msg,null,detailErrorMsg);
    }
    private static <T> Res<T> returnRes(int code, String msg,T data,T detailErrorMsg) {
        Res<T> res = new Res<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        res.setDetailErrorMsg(detailErrorMsg);
        return res;
    }

}
