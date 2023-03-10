package com.fu.springbootdemo.global;

/**
 * 特殊异常
 */
public enum Code {
    NOT_LOGIN(2,"抱歉，您未认证身份，请先登录认证身份。"),
    NOT_PURVIEW(3,"抱歉，您当前没有访问权限，请联系管理员授权。");

    private final int errCode;
    private final String errMessage;

    Code(int errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
