package com.fu.springbootdemo;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class ToPdfTests {

    @SneakyThrows
    @Test
    public void test(){
        Document document = new Document("D:/斗破苍穹.docx");
        document.save("斗破苍穹.epub",SaveFormat.EPUB);
    }

}
