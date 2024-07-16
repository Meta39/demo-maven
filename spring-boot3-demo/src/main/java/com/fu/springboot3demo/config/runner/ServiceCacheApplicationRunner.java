package com.fu.springboot3demo.config.runner;

import com.fu.springboot3demo.dto.ServiceInfo;
import com.fu.springboot3demo.service.GenericService;
import com.fu.springboot3demo.util.ApplicationContextUtils;
import com.fu.springboot3demo.util.ServiceCacheUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 启动后把所有实现了 GenericService 接口的类写入缓存。这样在调用方法的时候就可以直接获取类进行方法调用。
 *
 * @since 2024-07-16
 */
@Component
public class ServiceCacheApplicationRunner implements ApplicationRunner {

    @Override
    @SuppressWarnings("unchecked")
    public void run(ApplicationArguments args) throws NoSuchMethodException {
        String[] beanNames = ApplicationContextUtils.getBeanNamesForType(GenericService.class);
        for (String beanName : beanNames) {
            GenericService<Object> service = (GenericService<Object>) ApplicationContextUtils.getBean(beanName);
            Type[] genericInterfaces = service.getClass().getGenericInterfaces();
            ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];
            Class<Object> requestType = (Class<Object>) parameterizedType.getActualTypeArguments()[0];
            Method method = service.getClass().getMethod("invoke", requestType);
            // 显式类型转换
            ServiceInfo<Object> serviceInfo = new ServiceInfo<>(service, method, requestType);
            //写入缓存
            ServiceCacheUtils.cache.put(beanName, serviceInfo);
        }
    }

}
