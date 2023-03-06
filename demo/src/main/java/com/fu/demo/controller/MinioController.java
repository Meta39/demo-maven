package com.fu.demo.controller;

import com.fu.demo.minio.MinioTemplate;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("minio")
public class MinioController {
    @Resource
    private MinioTemplate minioTemplate;

    /**
     * 文件上传
     * @param file 文件
     */
    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) throws InvalidBucketNameException, InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidPortException, InvalidKeyException, InvalidEndpointException, XmlParserException, InternalException, RegionConflictException, ErrorResponseException, InvalidResponseException, ServerException {
        return minioTemplate.upload(file);
    }

    /**
     * 删除文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     */
    @PostMapping("delete")
    public void delete(@RequestParam("bucketName") String bucketName,@RequestParam("fileName") String fileName) throws InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidPortException, InvalidKeyException, InvalidEndpointException, InvalidResponseException, XmlParserException, InternalException, ServerException {
        minioTemplate.delete(bucketName, fileName);
    }
}
