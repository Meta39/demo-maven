package com.fu.basedemo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压/解缩 + AES对称加/解密
 */
public class CompressionEncryptionTests {

    @SneakyThrows
    @Test
    void test(){
        String originalData = "那么，为什么要压缩任何东西？ 很简单，因为这是减少必须通过网络传送或存储到磁盘的数据量的好方法，因此可以提高操作速度。 根据文档的性质，典型的文本文件或消息可以减少10倍或更多。 当然，您将不得不考虑压缩和解压缩的成本，但是当您拥有大量数据时，这些成本将不会很大。";
        System.out.println("未压缩byte[]长度：" + originalData.getBytes(StandardCharsets.UTF_8).length);

        // 压缩（设置字节编码为UTF-8）
        byte[] compressedData = compress(originalData.getBytes(StandardCharsets.UTF_8));
        System.out.println("压缩后byte[]长度：" + compressedData.length);

        // 加密
        String encryptionKey = "abcdefghijklmnop"; // 用于加密的密钥
        byte[] encryptedData = encrypt(compressedData, encryptionKey);

        // 解密
        byte[] decryptedData = decrypt(encryptedData, encryptionKey);

        // 解压缩
        byte[] decompressedData = decompress(decryptedData);

        // 将解压缩后的字节转换回字符串并打印（设置字节编码为UTF-8）
        String result = new String(decompressedData, StandardCharsets.UTF_8);
        System.out.println("解密数据：" + result);
    }

    // 压缩数据
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)) {
            gzipStream.write(data);
        }
        return byteStream.toByteArray();
    }

    // 解压缩数据
    public static byte[] decompress(byte[] compressedData) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (GZIPInputStream gzipStream = new GZIPInputStream(new ByteArrayInputStream(compressedData))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipStream.read(buffer)) > 0) {
                byteStream.write(buffer, 0, len);
            }
        }
        return byteStream.toByteArray();
    }

    // 加密数据
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(keyBytes));
        return cipher.doFinal(data);
    }

    // 解密数据
    public static byte[] decrypt(byte[] encryptedData, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(keyBytes));
        return cipher.doFinal(encryptedData);
    }

}
