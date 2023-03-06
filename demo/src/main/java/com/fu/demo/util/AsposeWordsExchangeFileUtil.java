package com.fu.demo.util;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.fu.demo.entity.Err;
import com.fu.demo.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件转换工具类
 */
@Slf4j
public class AsposeWordsExchangeFileUtil {

    /**
     * 转换后的文件存储位置
     */
    public static final String tempPath = System.getProperty("user.dir");
    /**
     * 文件存放目录
     */
    public static final String FILE_DIR = "/ExchangeFile/";

    /**
     * 文件类型转换
     *
     * @param file         上传的文件
     * @param exchangeType 需要转换的文件类型
     */
    public static String ExchangeFile(MultipartFile file, String exchangeType) throws IOException {
        if (!file.isEmpty()) {
            // 如果存放目录不存在则创建
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date(System.currentTimeMillis());
            //创建年/月/日文件夹以/结尾
            String dateFile = sdf.format(date) + "/";
            File saveDir = new File(tempPath + FILE_DIR + dateFile);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            //生成的文件名称
            String exchangeFileName = UUID.randomUUID() + "." + exchangeType;
            //生成文件的输出路径
            File exchangeFile = new File(saveDir + "/" + exchangeFileName);
            if (!exchangeFile.exists()) {
                exchangeFile.createNewFile();
            }
            //输出文件流，用try包裹住就会自动关闭流
            try(FileOutputStream os = new FileOutputStream(exchangeFile)){
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
                    //不支持转换的类型
                    throw new Err(Status.FILE_UNKNOWN_TYPE.getStatus(), Status.FILE_UNKNOWN_TYPE.getMsg());
                }
            }catch (Exception e){
                log.error("文件类型转换异常",e);
            }
            return dateFile + exchangeFileName;
        } else {
            throw new Err(Status.FILE_IS_EMPTY.getStatus(), Status.FILE_IS_EMPTY.getMsg());
        }
    }
}
