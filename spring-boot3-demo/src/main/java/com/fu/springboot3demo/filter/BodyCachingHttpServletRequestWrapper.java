package com.fu.springboot3demo.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.*;

public class BodyCachingHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;
    private final ServletInputStreamWrapper inputStreamWrapper;

    public BodyCachingHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int length;
        while ((length = request.getInputStream().read(buffer)) != -1) {
            outStream.write(buffer, 0, length);
        }

        this.body = outStream.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
        this.inputStreamWrapper = new ServletInputStreamWrapper(byteArrayInputStream);
        resetInputStream();
    }

    private void resetInputStream() {
        this.inputStreamWrapper.setInputStream(new ByteArrayInputStream(this.body != null ? this.body : new byte[0]));
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() {
        return this.inputStreamWrapper;
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.inputStreamWrapper));
    }


    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    private static class ServletInputStreamWrapper extends ServletInputStream {

        private InputStream inputStream;

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }
    }
}