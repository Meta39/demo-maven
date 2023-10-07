package com.fu.springbootdemo.global;

/**
 * 特殊异常
 */
public enum Code {
    NOT_LOGIN(2,"抱歉，您未认证身份，请先登录认证身份。"),
    NOT_PURVIEW(3,"抱歉，您当前没有访问权限，请联系管理员授权。"),
    ENCRYPT_ALGORITHM_ERROR(4,"获取加密算法单例失败，请检查加密算法是否正确。"),
    ENCRYPT_ERROR(5,"加密失败，详情请查看错误日志。"),
    DECRYPT_ERROR(6,"解密失败，详情请查看错误日志。"),
    KEY_PAIR_TIMEOUT_ERROR(7,"密钥对已过期，请重新请求生成接口生成新的密钥对。");

    private final Integer errCode;
    private final String errMessage;

    Code(Integer errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
