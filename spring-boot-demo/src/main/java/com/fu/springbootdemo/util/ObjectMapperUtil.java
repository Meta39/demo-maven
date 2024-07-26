package com.fu.springbootdemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fu.springbootdemo.global.GlobalVariable.OBJECT_MAPPER;

/**
 * ObjectMapper饿汉式单例模式工具类
 */
public class ObjectMapperUtil {
    private static final Logger log = LoggerFactory.getLogger(ObjectMapperUtil.class);
    //该工具类应当只有一个实例对象，所以私有化构造函数防止其它人new一个工具类调用其中的方法。
    private ObjectMapperUtil(){}

    /**
     * 序列化
     * @param object 对象
     */
    public static String writeValueAsString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化异常："+e.getMessage());
        }
    }

    /**
     * 反序列化
     * @param content json字符串
     * @param valueType 对象.Class
     */
    public static <T> T obj(String content, Class<T> valueType){
        try {
            return OBJECT_MAPPER.readValue(content,valueType);
        } catch (JsonProcessingException e) {
            log.error("ObjectMapperUtil反序列化异常：",e);
            throw new RuntimeException("反序列化异常："+e.getMessage());
        }
    }

}
