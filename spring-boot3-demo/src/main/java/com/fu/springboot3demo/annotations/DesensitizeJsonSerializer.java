package com.fu.springboot3demo.annotations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fu.springboot3demo.enums.Desensitization;

import java.io.IOException;
import java.util.Objects;

/**
 * 创建日期：2024-05-09
 */
public class DesensitizeJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {
    private Desensitization desensitization;

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(desensitization.serialize().apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Desensitize desensitize = beanProperty.getAnnotation(Desensitize.class);
        if (Objects.nonNull(desensitize) && Objects.equals(String.class, beanProperty.getType().getRawClass())) {
            this.desensitization = desensitize.value();
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }

}
