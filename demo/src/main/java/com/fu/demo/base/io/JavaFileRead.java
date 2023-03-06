package com.fu.demo.base.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

/**
 * 磁盘操作
 */
@Slf4j
public class JavaFileRead {

    /**
     * File 类可以用于表示文件和目录的信息，但是它不表示文件的内容。
     * 从 Java7 开始，可以使用 Paths 和 Files 代替 File。
     */
    public static void main(String[] args) {
        //获取文件或目录
        File dir = new File("E:/File");
        showAllFiles(dir);
    }

    public static void showAllFiles(File dir){
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            log.info("文件名称：{}",dir.getName());
            return;
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            showAllFiles(file);
        }
    }
}
