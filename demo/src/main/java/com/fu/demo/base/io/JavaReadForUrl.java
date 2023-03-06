package com.fu.demo.base.io;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 网络操作
 */
@Slf4j
public class JavaReadForUrl {

    @Test
    @SneakyThrows
    public void readForUrl(){
        URL url = new URL("https://www.baidu.com");

        /* 字节流 */
        InputStream is = url.openStream();

        /* 字符流 */
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);

        /* 提供缓存功能 */
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ((line = br.readLine()) != null) {
            log.info(line);
        }
        br.close();
    }
}
