package com.fu.demo;

import com.fu.demo.entity.Err;
import com.fu.demo.entity.Res;
import com.fu.demo.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    /**
     * 自定义异常
     */
    @ExceptionHandler(value =Err.class)
    public Res Err(Err e){
        log.error("自定义Err异常：",e);
//        return Res.err(e.getCode(), e.getMsg());
        return Res.err(e.getCode(),e.getMsg(),e);
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(value =NullPointerException.class)
    public Res exceptionHandler(NullPointerException e){
        log.error("空指针NullPointerException异常：",e);
//        return Res.err(Status.NULL_POINTER_EXCEPTION.getStatus(),Status.NULL_POINTER_EXCEPTION.getMsg());
        return Res.err(Status.NULL_POINTER_EXCEPTION.getStatus(),Status.NULL_POINTER_EXCEPTION.getMsg(),e);
    }

    /**
     * 500异常
     */
    @ExceptionHandler(value =Exception.class)
    public Res exception(Exception e){
        log.error("未知Exception异常：",e);
//        return Res.err(Status.FAIL.getStatus(), Status.FAIL.getMsg());
        return Res.err(Status.FAIL.getStatus(), Status.FAIL.getMsg(),e);
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Res missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数MissingServletRequestParameterException异常：{}",e.getParameterName());
//        return Res.err(Status.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getStatus(), Status.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getMsg() + e.getParameterName());
        return Res.err(Status.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getStatus(), Status.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getMsg() + e.getParameterName(),e);
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Res bindException(BindException e) {
        log.error("参数校验BindException异常：{}", Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
//        return Res.err(Status.BIND_EXCEPTION.getStatus(),Status.BIND_EXCEPTION.getMsg() + e.getBindingResult().getFieldError().getDefaultMessage());
        return Res.err(Status.BIND_EXCEPTION.getStatus(),Status.BIND_EXCEPTION.getMsg() + e.getBindingResult().getFieldError().getDefaultMessage(),e);
    }

    /**
     * 数据库异常
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public Res MyBatisSystemException(MyBatisSystemException e) {
        log.error("数据库MyBatisSystemException异常（有可能是连接不上数据库，请再试一次访问，以启动备用数据库！）：",e);
//        return Res.err(Status.MYBATIS_SYSTEM_EXCEPTION.getStatus(), Status.MYBATIS_SYSTEM_EXCEPTION.getMsg() + e.getMessage());
        return Res.err(Status.MYBATIS_SYSTEM_EXCEPTION.getStatus(), Status.MYBATIS_SYSTEM_EXCEPTION.getMsg() + e.getMessage(),e);
    }

}
