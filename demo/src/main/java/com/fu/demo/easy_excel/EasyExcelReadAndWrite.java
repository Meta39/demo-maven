package com.fu.demo.easy_excel;


import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EasyExcelReadAndWrite {

    private List<DemoData> data(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串"+i);
            data.setDate(new Date());
            data.setADouble(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 根据list写入excel
     */
    @Test
    public void simpleWrite(){
        String fileName = "D:/EasyExcel.xlsx";
        //write(fileName, 格式类)  sheet（表名）   doWrite(数据list)
        EasyExcel.write(fileName,DemoData.class).sheet("1").doWrite(data());
    }

    /**
     * 把excel读取出来
     */
    @Test
    public void simpleRead() {
        String fileName = "D:/EasyExcel.xlsx";
        EasyExcel.read(fileName,DemoData.class,new DemoDataListener()).sheet().doRead();
    }

}
