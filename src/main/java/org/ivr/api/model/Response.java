package org.ivr.api.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author zhangyaoxin@yiwise.com
 * @description controller return value
 * @create 2019/04/02
 **/
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Response<T> {

    Integer code = ResponseCode.SUCCESS.getCode();

    T data;

    private Response(T data) {
        this.data = data;
    }

    private Response(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static <T> Response<T> fail(Integer code, T data) {
        return new Response<>(code, data);
    }

}
