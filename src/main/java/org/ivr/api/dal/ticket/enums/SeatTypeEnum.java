package org.ivr.api.dal.ticket.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/09
 **/
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum SeatTypeEnum {

    //
    SEAT1("商务座", 32, "A9", Pattern.compile("商务|特等")),
    SEAT2("一等座", 31, "M", Pattern.compile("一等")),
    SEAT3("二等座", 30, "O", Pattern.compile("二等")),
    SEAT4("软卧", 23, "A4", Pattern.compile("软卧")),
    SEAT5("硬卧", 28, "A3", Pattern.compile("硬卧")),
    SEAT6("硬座", 29, "A1", Pattern.compile("硬座")),
    SEAT7("无座", 26, "WZ", Pattern.compile("无座|站"));

    @Getter
    String name;

    @Getter
    int fieldNum;

    String priceMark;

    @Getter
    Pattern pattern;

    public static Optional<SeatTypeEnum> getByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        }
        for (SeatTypeEnum seatType : values()) {
            if (name.contains(seatType.getName())) {
                return Optional.of(seatType);
            }
        }
        return Optional.empty();
    }

    public static Optional<SeatTypeEnum> getByPriceMark(String priceMark) {
        Assert.notNull(priceMark, "查询座位使用的价格代号为空");
        for (SeatTypeEnum seatType : values()) {
            if (seatType.priceMark.equals(priceMark)) {
                return Optional.of(seatType);
            }
        }
        return Optional.empty();
    }

    public static Optional<SeatTypeEnum> getByPattern(String input) {
        for (SeatTypeEnum seatType : values()) {
            Matcher matcher = seatType.getPattern().matcher(input);
            if (matcher.find()) {
                return Optional.of(seatType);
            }
        }
        return Optional.empty();
    }
}
