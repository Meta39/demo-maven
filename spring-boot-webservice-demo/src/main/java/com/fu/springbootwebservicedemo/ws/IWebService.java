package com.fu.springbootwebservicedemo.ws;

/**
 * 统一 post 调用
 * 创建日期：2024-07-01
 */
public interface IWebService {

    R<?> handle(String parameter);

}
