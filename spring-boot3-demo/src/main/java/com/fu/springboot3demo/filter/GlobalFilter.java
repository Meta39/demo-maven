package com.fu.springboot3demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局过滤器，过滤请求。
 */
@Slf4j
@Component
@WebFilter(filterName = "globalFilter", urlPatterns = "/*")
public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        BodyCachingHttpServletRequestWrapper requestWrapper = new BodyCachingHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        BodyCachingHttpServletResponseWrapper responseWrapper = new BodyCachingHttpServletResponseWrapper((HttpServletResponse) servletResponse);
        byte[] requestBodyByte = requestWrapper.getBody();

        //过滤请求内容，去除空格和换行符
        String requestBodyString = new String(requestBodyByte);
        Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
        Matcher matcher = pattern.matcher(requestBodyString);//请求
        String requestBodyFilterString = matcher.replaceAll("");//全部替换成空格

        log.info("请求内容:{}", requestBodyFilterString);
        filterChain.doFilter(requestWrapper, responseWrapper);

        //要在doFilter后面获取body，否则会无法获取到数据。
        byte[] responseBodyByte = responseWrapper.getBody();
        String responseBodyString = new String(responseBodyByte);
        log.info("返回内容:{}", responseBodyString);
    }

}
