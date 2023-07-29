package com.fu.springbootdemo.service.impl;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.fu.springbootdemo.global.Err;
import com.fu.springbootdemo.service.ToolboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ToolboxServiceImpl implements ToolboxService {

    @Override
    public String wordToPdf(MultipartFile file, String exchangeType) {
        if (file.isEmpty()) {
            throw Err.msg("文件不能为空！");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(System.currentTimeMillis());
        String dateFile = sdf.format(date) + "/";
        File saveDir = new File(System.getProperty("user.dir") + "/" + dateFile);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        //生成的文件名称
        String exchangeFileName = UUID.randomUUID() + "." + exchangeType;
        //生成文件的输出路径
        File exchangeFile = new File(saveDir + "/" + exchangeFileName);
        if (!exchangeFile.exists()) {
            try {
                exchangeFile.createNewFile();
            } catch (IOException e) {
                log.error("创建转换类型为" + exchangeType + "的文件失败", e);
                throw Err.msg("创建转换类型为" + exchangeType + "的文件失败");
            }
        }
        //try-with自动关闭流
        try (FileOutputStream os = new FileOutputStream(exchangeFile)) {
            //获取上传的文件流
            Document doc = new Document(file.getInputStream());
            //支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            if ("doc".equals(exchangeType)) {
                doc.save(os, SaveFormat.DOC);
            } else if ("docx".equals(exchangeType)) {
                doc.save(os, SaveFormat.DOCX);
            } else if ("pdf".equals(exchangeType)) {
                doc.save(os, SaveFormat.PDF);
            } else if ("html".equals(exchangeType)) {
                doc.save(os, SaveFormat.HTML);
            } else if ("markdown".equals(exchangeType)) {
                doc.save(os, SaveFormat.MARKDOWN);
            } else if ("png".equals(exchangeType)) {
                doc.save(os, SaveFormat.PNG);
            } else if ("jpeg".equals(exchangeType)) {
                doc.save(os, SaveFormat.JPEG);
            } else if ("gif".equals(exchangeType)) {
                doc.save(os, SaveFormat.GIF);
            } else {
                if (exchangeFile.exists()) {
                    exchangeFile.delete();
                }
                //不支持转换的类型
                //抛出RuntimeException()
                throw Err.msg("不支持该类型的转换");
            }
        } catch (IOException e) {
            log.error("输出成" + exchangeType + "文件转换异常", e);
            throw Err.msg("输出成" + exchangeType + "文件转换异常");
        } catch (Exception e) {
            //Document
            log.error("文件转换异常：", e);
            throw Err.msg("文件转换异常");
        }
        return dateFile + exchangeFileName;
    }

}
