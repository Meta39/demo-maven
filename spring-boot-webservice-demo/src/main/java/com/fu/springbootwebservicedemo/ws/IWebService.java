package com.fu.springbootwebservicedemo.ws;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 统一 post 调用
 * 创建日期：2024-07-01
 */
public interface IWebService {

    R<?> handle(String parameter) throws JsonProcessingException;

}
