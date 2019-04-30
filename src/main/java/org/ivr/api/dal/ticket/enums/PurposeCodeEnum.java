package org.ivr.api.dal.ticket.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author zhangyaoxin@yiwise.com
 * @description 车票类型
 * @create 2019/04/04
 **/
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum PurposeCodeEnum {

    // 成人票
    ADULT("ADULT"),

    // 学生票
    STUDENT("0X00");

    PurposeCodeEnum(String code) {
        this.code = code;
    }

    @Getter
    private String code;
}
