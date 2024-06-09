package com.fu.springboot3demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@SpringBootTest
public class RestClientAndRestTemplateTests {
    @Autowired
    private RestClient restClient;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * RestTemplate 发送携带参数的的 get 请求
     */
    @Test
    void testRestTemplateGet() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        headers.setContentType(MediaType.APPLICATION_JSON);
        //构建请求参数 UriComponentsBuilder 有坑
        String uri = UriComponentsBuilder
                .fromUriString("/test")
                .queryParam("param", "get 参数").build(StandardCharsets.UTF_8).toString();
        //在 URL 编码中，非 ASCII 字符（比如中文字符）会被转换成 % 开头的十六进制表示。%20 表示空格
        String decodeUri = URLDecoder.decode(uri, StandardCharsets.UTF_8);
        log.info("decodeUri: {}", decodeUri);
        byte[] responseBodyByte = restTemplate.exchange(decodeUri, HttpMethod.GET, new HttpEntity<>("请求体内容", headers), byte[].class).getBody();
        String responseBodyString = new String(responseBodyByte, StandardCharsets.UTF_8);
        log.info("responseBodyString:{}", responseBodyString);
    }

    /**
     * RestTemplate 发送携带参数的的 post 请求
     */
    @Test
    void testRestTemplatePost() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("zdy", "zdy header");
        //构建请求参数 UriComponentsBuilder 有坑
        String uri = UriComponentsBuilder
                .fromUriString("/test")
                .queryParam("param", "post 参数").build(StandardCharsets.UTF_8).toString();
        //在 URL 编码中，非 ASCII 字符（比如中文字符）会被转换成 % 开头的十六进制表示。%20 表示空格
        String decodeUri = URLDecoder.decode(uri, StandardCharsets.UTF_8);
        log.info("decodeUri: {}", decodeUri);
        byte[] responseBodyByte = restTemplate.exchange(decodeUri, HttpMethod.POST, new HttpEntity<>("请求体内容", headers), byte[].class).getBody();
        String responseBodyString = new String(responseBodyByte, StandardCharsets.UTF_8);
        log.info("responseBodyString:{}", responseBodyString);
    }

    /**
     * RestClient 发送携带参数的的 get 请求
     */
    @Test
    void testRestClientGet() {
        //因为已经在 RestClient 配置了 baseUrl，直接写uri即可。直接转 String 可能会乱码。稳妥办法先转 byte[] 再设置编码为 UTF-8 转 String
        byte[] bodyByte = restClient
                .get()
//                .uri("/test")//发送不带参数的请求
                .uri(uriBuilder -> uriBuilder
                        .path("/test")
                        .queryParam("param", "get 参数")
                        .build())
                .retrieve()
                .body(byte[].class);
        String bodyString = new String(bodyByte, StandardCharsets.UTF_8);
        log.info("responseBodyString:{}", bodyString);
    }

    /**
     * RestClient 发送 post 请求
     */
    @Test
    void testRestClientPost() {
        byte[] bodyByte = restClient
                .post()
//                .uri("/test")
                .uri(uriBuilder -> uriBuilder
                        .path("/test")
                        .queryParam("param", "post 参数")
                        .build())
                .header("zdy", "zdy header")//自定义请求头【不要有中文，会乱码】
                .body("请求体内容")//requestBody 请求体
//                .body(new HashMap<>(){{put("key", "value");}}, new ParameterizedTypeReference<Map<String,Object>>() {})//requestBody 请求体，复杂泛型
                .retrieve()
                .body(byte[].class);//responseBody 响应体
        String bodyString = new String(bodyByte, StandardCharsets.UTF_8);
        log.info("responseBodyString:{}", bodyString);
    }

}
