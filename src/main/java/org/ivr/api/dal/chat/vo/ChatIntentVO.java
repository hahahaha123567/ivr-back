package org.ivr.api.dal.chat.vo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/30
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class ChatIntentVO {

    Integer code;

    String intentName;

    String actionName;

    Map<String, String> parameters;
}
