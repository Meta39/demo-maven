package com.fu.demo.controller;

import com.fu.demo.util.AsposeWordsExchangeFileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件类型转换
 */
@RestController
@RequestMapping("exchangeFile")
public class ExchangeFileController {

    /**
     * 文件类型转换（需要开放）
     * @param file 上传的文件
     * @param exchangeType 需要转换的文件类型
     * @return
     * @throws Exception
     */
    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam String exchangeType) throws Exception {
        return AsposeWordsExchangeFileUtil.ExchangeFile(file,exchangeType);
    }

    /**
     * 删除文件
     * @param filePath 文件URI
     */
    @PostMapping("delete")
    public void delete(@RequestParam("filePath") String filePath){
        File file = new File(AsposeWordsExchangeFileUtil.tempPath+AsposeWordsExchangeFileUtil.FILE_DIR+filePath);
        if (file.exists()){
            file.delete();
        }
    }
}
