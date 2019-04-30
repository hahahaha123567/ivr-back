package org.ivr.api.helper;

import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/08
 **/
public class TimeHelper {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Getter
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static String toString(LocalDate localDate) {
        Assert.notNull(localDate, "要转换的日期为空");
        return localDate.format(dateFormatter);
    }

    public static String toString(LocalTime localTime) {
        Assert.notNull(localTime, "要转换的时间为空");
        return localTime.format(timeFormatter);
    }
}
