package com.fu.springbootwebservicedemo.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fu.springbootwebservicedemo.util.ApplicationContextUtils;
import com.fu.springbootwebservicedemo.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

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
     * @param parameter 请求参数
     */
    @WebMethod
    public String invoke(@WebParam(name = "service") String service, @WebParam(name = "parameter") String parameter) throws JsonProcessingException {
        IWebService webService = (IWebService) ApplicationContextUtils.getBean(service);
        R<?> r;
        try {
            r = webService.handle(parameter);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            r = R.err(e.getMessage());
        }
        return JacksonUtils.XML.writeValueAsString(r);
    }

}
