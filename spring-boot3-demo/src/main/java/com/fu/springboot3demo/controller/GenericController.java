package com.fu.springboot3demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.springboot3demo.dto.ServiceInfo;
import com.fu.springboot3demo.util.ServiceCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 通用入口
 *
 * @since 2024-07-16
 */
@Slf4j
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class GenericController {
    private final ObjectMapper objectMapper;

    @PostMapping("/{serviceName}")
    public Object invokeService(@PathVariable String serviceName, @RequestBody(required = false) Map<String, Object> requestBody) throws InvocationTargetException, IllegalAccessException {
//        log.info("{}接口入参：{}", serviceName, requestBody);
        // 从缓存获取ServiceInfo
        ServiceInfo<?> serviceInfo = ServiceCacheUtils.cache.get(serviceName);
        //这里可以进行判空，但是没必要。没有的就让它抛异常。
        // 将请求参数转换为具体的类型
        Object requestObject = objectMapper.convertValue(requestBody, serviceInfo.getRequestType());
        // 调用Service的invoke方法并获取返回值
        Object responseObject = serviceInfo.invoke(requestObject);
//        log.info("{}接口出参：{}", serviceName, responseObject);
        return responseObject;
    }

}