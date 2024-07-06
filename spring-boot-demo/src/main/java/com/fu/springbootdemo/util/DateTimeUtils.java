package com.bsoft.bsjkt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author zhongyf
 * @since 2024-07-05
 */
public class DateTimeUtils {
    private DateTimeUtils() {
    }

    //自定义格式(没有的格式在自定义格式后面加，不要在默认格式后面加！)
    public static final String DIY_TIME_FORMAT_NO_SECOND = "HH:mm";
    public static final String DIY_TIME_FORMAT = "HHmmss";
    public static final String DIY_DATE_FORMAT = "yyyyMMdd";
    public static final String DIY_DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    // 默认格式
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Date to LocalDate
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // Date to LocalTime
    public static LocalTime dateToLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    // Date to LocalDateTime
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // LocalDate to Date
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // LocalTime to Date (current date with specified time)
    public static Date localTimeToDate(LocalTime localTime) {
        return Date.from(localTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
    }

    // LocalDateTime to Date
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // String to LocalDate with default format
    public static LocalDate stringToLocalDate(String dateStr) {
        return stringToLocalDate(dateStr, DEFAULT_DATE_FORMAT);
    }

    // String to LocalDate with specified format
    public static LocalDate stringToLocalDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, formatter);
    }

    // String to LocalTime with default format
    public static LocalTime stringToLocalTime(String timeStr) {
        return stringToLocalTime(timeStr, DEFAULT_TIME_FORMAT);
    }

    // String to LocalTime with specified format
    public static LocalTime stringToLocalTime(String timeStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(timeStr, formatter);
    }

    // String to LocalDateTime with default format
    public static LocalDateTime stringToLocalDateTime(String dateTimeStr) {
        return stringToLocalDateTime(dateTimeStr, DEFAULT_DATE_TIME_FORMAT);
    }

    // String to LocalDateTime with specified format
    public static LocalDateTime stringToLocalDateTime(String dateTimeStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    // LocalDate to String with default format
    public static String localDateToString(LocalDate localDate) {
        return localDateToString(localDate, DEFAULT_DATE_FORMAT);
    }

    // LocalDate to String with specified format
    public static String localDateToString(LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    // LocalTime to String with default format
    public static String localTimeToString(LocalTime localTime) {
        return localTimeToString(localTime, DEFAULT_TIME_FORMAT);
    }

    // LocalTime to String with specified format
    public static String localTimeToString(LocalTime localTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localTime.format(formatter);
    }

    // LocalDateTime to String with default format
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTimeToString(localDateTime, DEFAULT_DATE_TIME_FORMAT);
    }

    // LocalDateTime to String with specified format
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    // String to Date with default format
    public static Date stringToDate(String dateStr) {
        return stringToDate(dateStr, DEFAULT_DATE_TIME_FORMAT);
    }

    // String to Date with specified format
    public static Date stringToDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Date to String with default format
    public static String dateToString(Date date) {
        return dateToString(date, DEFAULT_DATE_TIME_FORMAT);
    }

    // Date to String with specified format
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

}