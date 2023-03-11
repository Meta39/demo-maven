package com.fu.springbootdemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.springbootdemo.global.Err;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ObjectMapper饿汉式单例模式工具类
 */
public class ObjectMapperUtil {
    private static final Logger log = LoggerFactory.getLogger(ObjectMapperUtil.class);

    private ObjectMapperUtil(){

    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance(){
        return objectMapper;
    }

    /**
     * 序列化
     * @param object 对象
     */
    public static String writeValueAsString(Object object) {
        try {
            return getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("ObjectMapperUtil序列化异常：",e);
            throw Err.setMessage("序列化异常："+e.getMessage());
        }
    }

    /**
     * 反序列化
     * @param content json字符串
     * @param valueType 对象.Class
     */
    public static <T> T obj(String content, Class<T> valueType){
        try {
            return getInstance().readValue(content,valueType);
        } catch (JsonProcessingException e) {
            log.error("ObjectMapperUtil反序列化异常：",e);
            throw Err.setMessage("反序列化异常："+e.getMessage());
        }
    }

}
