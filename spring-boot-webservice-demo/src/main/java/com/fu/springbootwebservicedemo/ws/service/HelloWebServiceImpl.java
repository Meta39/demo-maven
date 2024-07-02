package com.fu.springbootwebservicedemo.ws.service;

import com.fu.springbootwebservicedemo.ws.IWebService;
import com.fu.springbootwebservicedemo.ws.R;
import org.springframework.stereotype.Service;

/**
 * 实现 IWebService 接口，统一调用
 * 创建日期：2024-07-02
 */
@Service("Hello")//到时候会通过 http post 调用 <service>Hello</service>
public class HelloWebServiceImpl implements IWebService {

    @Override
    public R<?> handle(String parameter) {
        return R.ok("hello web service.");
    }

}
