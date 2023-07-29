package com.fu.basedemo.jdk8;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * JDK8日期工具类
 */
public class LocalDateTest {

    @Test
    public void test() {
        //获取当前时间
        System.out.println("获取当前时间yyyy-MM-dd：" + LocalDate.now());//yyyy-MM-dd
        System.out.println("获取当前时间hh:mm:ss：" + LocalTime.now());//hh:mm:ss
        System.out.println("获取当前时间yyyy-MM-ddThh:mm:ss：" + LocalDateTime.now());//yyyy-MM-ddThh:mm:ss
        //将数字转换成相应的时间
        System.out.println("将数字转换成相应的时间yyyy-MM-dd：" + LocalDate.of(2023, 3, 15));//yyyy-MM-dd
        System.out.println("将数字转换成相应的时间hh:mm:ss：" + LocalTime.of(21, 2, 30));//hh:mm:ss
        System.out.println("将数字转换成相应的时间yyyy-MM-ddThh:mm:ss：" + LocalDateTime.of(2023, 3, 15, 21, 2, 30));//yyyy-MM-ddThh:mm:ss
        //日期格式转换
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //格式化：将日期转换成指定字符串
        LocalDateTime now = LocalDateTime.now();
        String strDataTime = dtf.format(now);
        System.out.println("格式化：" + strDataTime);//将日期转换成指定格式字符串

        //解析：字符串转日期
        TemporalAccessor parseDataTime = dtf.parse("2023-03-15 21:14:10");
        LocalDateTime fromDateTime = LocalDateTime.from(parseDataTime);
        System.out.println("解析：" + fromDateTime);
    }

    /**
     * LocalTime时间按半小时向上/下取整
     */
    @Test
    void localTimeCeil30Minutes() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime currentTime = LocalTime.parse("08:59", dateTimeFormatter);
        LocalTime foodTime = currentTime.withMinute(currentTime.getMinute() / 30 * 30);//按30分钟向下取整
        LocalTime ceilTime = foodTime.plusMinutes(30L);//返回时间+30分钟的时间
        System.out.println(foodTime + "-" + ceilTime);
    }

}
