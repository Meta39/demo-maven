package com.fu.demo.fastdfs;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * FastDFS文件管理工具类
 */
@Slf4j
@Component
public class FastDFSUtils {

    @Resource
    private FastFileStorageClient fastFileStorageClient;

    public String uploadFile(MultipartFile file) throws IOException {
        return fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null).getFullPath();
    }

    public Boolean deleteFile(String filePath) {
        if (!StringUtils.hasLength(filePath)) {
            return false;
        }
        StorePath storePath = StorePath.parseFromUrl(filePath);
        fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        return true;
    }
}
