package cn.wdz.vertx.web.common.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 基于 JDK 8 time包的时间工具类
 * @author wdz
 * @date 2019/12/14
 */
public class TimeUtils {

  /**
   * 获取默认时间格式: yyyy-MM-dd HH:mm:ss
   */
  private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.LONG_DATE_PATTERN_LINE.formatter;
  private static final SimpleDateFormat DEFAULT_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private TimeUtils() {
    // no construct function
  }

  /**
   * Long 转时间
   *
   * @param timestamp
   * @return
   */
  public static LocalDateTime parseTime(Long timestamp) {
    Instant instant = Instant.ofEpochMilli(timestamp);
    ZoneId zone = ZoneId.systemDefault();
    return LocalDateTime.ofInstant(instant, zone);
  }

  /**
   * String 转时间
   *
   * @param timeStr
   * @return
   */
  public static LocalDateTime parseTime(String timeStr) {
    return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
  }

  /**
   * String 转时间
   *
   * @param timeStr
   * @param format  时间格式
   * @return
   */
  public static LocalDateTime parseTime(String timeStr, TimeFormat format) {
    return LocalDateTime.parse(timeStr, format.formatter);
  }


  /**
   * 时间转 String
   *
   * @param time
   * @return
   */
  public static String parseTime(LocalDateTime time) {
    return DEFAULT_DATETIME_FORMATTER.format(time);
  }

  /**
   * 时间转 String
   *
   * @param time
   * @param format 时间格式
   * @return
   */
  public static String parseTime(LocalDateTime time, TimeFormat format) {
    return format.formatter.format(time);
  }

  /**
   * 获取当前时间
   *
   * @return
   */
  public static String getCurrentDatetime() {
    return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
  }

  /**
   * 获取当前时间
   *
   * @param format 时间格式
   * @return
   */
  public static String getCurrentDatetime(TimeFormat format) {
    return format.formatter.format(LocalDateTime.now());
  }

  /**
   * LocalDateTime to Date
   * @param localDateTime
   * @return
   */
  public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = localDateTime.atZone(zoneId);
    return Date.from(zdt.toInstant());
  }


  /**
   * LocalDateTime to Long
   * @param localDateTime
   * @return
   */
  public static Long LocalDateTimeToLong(LocalDateTime localDateTime) {
    return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
  }



  /**
   * 时间格式
   */
  public enum TimeFormat {

    /**
     * 短时间格式
     */
    SHORT_DATE_PATTERN_YM_LINE("yyyy-MM"),
    SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
    SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
    SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
    SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

    /**
     * 长时间格式
     */
    LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
    LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
    LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
    LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),
    LONG_DATE_PATTERN_NONE_LINE("yyyyMMddHHmmss"),

    /**
     * 长时间格式 带毫秒
     */
    LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS"),
    MONTH_DAY("MM/dd");
    ;

    private transient DateTimeFormatter formatter;

    TimeFormat(String pattern) {
      formatter = DateTimeFormatter.ofPattern(pattern);
    }
  }


}
