package com.fu.springbootdemo.global;

import java.io.Serializable;
import java.util.Objects;

/**
 * 自定义异常返回类
 */
public class Err extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 6558796578827818467L;

    private Integer errCode; //错误码

    private String errMessage; //错误信息

    private Err(){}//私有化构造方法

    private static final Err err = new Err();

    //全局异常捕获填写状态码和异常信息返回给前端
    public static Err setErr(Integer errCode,String errMessage){
        err.setErrCode(errCode);
        err.setErrMessage(errMessage);
        return err;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "Err{" +
                "errCode=" + errCode +
                ", errMessage='" + errMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Err err = (Err) o;
        return Objects.equals(errCode, err.errCode) && Objects.equals(errMessage, err.errMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errCode, errMessage);
    }

}
