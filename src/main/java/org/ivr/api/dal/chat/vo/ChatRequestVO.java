package org.ivr.api.dal.chat.vo;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/30
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRequestVO {

    Integer reqType;

    Map<String, Map> perception;

    Map<String, String> userInfo;

    public ChatRequestVO(String input,
                         @Value("${tuling.apiKey}") String apiKey,
                         @Value("${tuling.userId}") String userId) {
        // POST
        this.reqType = 0;
        this.perception = ImmutableMap.of(
                "inputText", ImmutableMap.of(
                        "text", input
                )
        );
        this.userInfo = ImmutableMap.of(
                "apiKey", apiKey,
                "userId", userId
        );
    }
}
