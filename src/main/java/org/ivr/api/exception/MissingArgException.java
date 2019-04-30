package org.ivr.api.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/12
 **/
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissingArgException extends RuntimeException {

    public MissingArgException(String message) {
        super(message);
    }
}
