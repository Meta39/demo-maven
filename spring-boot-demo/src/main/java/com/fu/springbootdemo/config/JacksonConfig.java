package com.fu.springbootdemo.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fu.springbootdemo.constant.DateAndTimeConstant;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Jackson 对 Java 8 新日期时间进行序列化和反序列化格式化
 * 创建日期：2024-05-24
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DateAndTimeConstant.DATE_TIME_PATTERN);
            // 把 Long 类型序列化为 String，当将 Java Long 类型的值传递给 JavaScript 时，可能会发生精度丢失。
//            builder.serializerByType(Long.class, ToStringSerializer.instance);
            //序列化
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateAndTimeConstant.DATE_TIME_FORMATTER));
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateAndTimeConstant.DATE_FORMATTER));
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DateAndTimeConstant.TIME_FORMATTER));
            //反序列化
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateAndTimeConstant.DATE_TIME_FORMATTER));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateAndTimeConstant.DATE_FORMATTER));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateAndTimeConstant.TIME_FORMATTER));
        };
    }

}
