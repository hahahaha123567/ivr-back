package org.ivr.api.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/02
 **/
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ResponseCode {

    // 请求成功
    SUCCESS(200, "OK"),
    // 参数有误
    BAD_REQUEST(400, "Bad Request")
    ;

    Integer code;

    String detail;

    ResponseCode(Integer code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public Integer getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }
}
