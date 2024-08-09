package com.fu.springbootwebservicedemo.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fu.springbootwebservicedemo.util.ApplicationContextUtils;
import com.fu.springbootwebservicedemo.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 创建日期：2024-07-01
 */
@Slf4j
@Service
@WebService
public class WebServiceEntry {

    /**
     * 通过实现了 IWebService 接口的 bean name 反射调用 handle 方法
     *
     * @param service   bean name
     * @param parameter XML 字符串请求参数
     */
    @WebMethod
    @SuppressWarnings("unchecked")
    public <T> String invoke(@WebParam(name = "service") String service, @WebParam(name = "parameter") String parameter) throws JsonProcessingException {
        IWebService<T> webService = (IWebService<T>) ApplicationContextUtils.getBean(service);

        // 获取 IWebService 实现类的泛型类型
        Type genericInterface = webService.getClass().getGenericInterfaces()[0];
        ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
        Class<T> parameterType = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        T reqObject = JacksonUtils.XML.readValue(parameter, parameterType);

        R<?> r;
        try {
            r = R.ok(webService.handle(reqObject));
        } catch (Exception e) {
            String message = e.getMessage();
            log.error(message, e);
            r = R.err(message);
        }
        return JacksonUtils.XML.writeValueAsString(r);
    }

}
