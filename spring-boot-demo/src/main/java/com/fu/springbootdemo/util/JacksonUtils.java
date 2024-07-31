package com.fu.springbootdemo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;

/**
 * jackson 工具类
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public abstract class JacksonUtils {
    public static final JsonMapper json = new JsonMapper();
    public static final XmlMapper xml = new XmlMapper();

    static {
        // json 配置
        json.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        json.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        json.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        json.registerModule(new JavaTimeModule());
        // xml 配置
        xml.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        xml.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        xml.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        xml.registerModule(new JavaTimeModule());
    }

    public static String writeValueAsStringJson(Object object) {
        try {
            return json.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValueJson(String content, TypeReference<T> valueTypeRef) {
        try {
            return json.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
