package com.fu.springbootdemo.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;

/**
 * 全局返回和异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 自定义异常
     */
    @ExceptionHandler(value =Err.class)
    public Res<Object> err(Err e){
        return Res.err(e.getErrCode(),e.getErrMessage());
    }

    /**
     * 服务器异常
     */
    @ExceptionHandler(value =Exception.class)
    public Res<Object> exception(Exception e){
        log.error("Exception异常：",e);
        return Res.err(e.getMessage());
    }

    /**
     * RequestParam注解请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Res<Object> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Res.err(e.getMessage());
    }

    //-----------------------------------------------有新的异常在上面加--------------------------------------------------------

    //-----------------------------------------------下面是全局返回-----------------------------------------------------------

    /**
     * 是否把返回内容存放到Res返回给前端
     * @param returnType 返回类型
     * @param converterType 转换器类型
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 把返回内容存放到Res返回给前端
     * @param body                  原始数据
     * @param returnType            原始返回给前端的数据类型
     * @param request               请求
     * @param response              响应
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ObjectMapper om = new ObjectMapper();
        if(body instanceof String){//String类型要特殊处理
            return om.writeValueAsString(Res.ok(body));
        } else if (body instanceof Res) {//本身是Res直接返回即可。例如：全局异常处理，返回的就是Res
            return body;
        }else if (body instanceof LinkedHashMap){//解决404、500等spring没有捕获的异常问题，只能放到最后的判断条件去判断
            LinkedHashMap map = (LinkedHashMap) body;//强转
            if (map.get("status") != null) {
                int status = (int) map.get("status");
                String error = (String) map.get("error");
                String message = (String) map.get("message");
                String path = (String) map.get("path");
                return Res.err(status, "error：" + error + ",message：" + message + ",path：" + path);
            }
        }
        return Res.ok(body);
    }

}
