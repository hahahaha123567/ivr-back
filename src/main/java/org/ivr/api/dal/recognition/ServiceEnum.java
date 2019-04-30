package org.ivr.api.dal.recognition;

import lombok.Getter;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/15
 **/
public enum ServiceEnum {

    //
    TICKET_QUERY(Pattern.compile("[到去]")),
    TICKET_QUERY_PRICE(Pattern.compile("多少钱|多贵|价格")),
    DEFAULT(Pattern.compile("niconiconi")),
    ;

    @Getter
    private Pattern pattern;

    ServiceEnum(Pattern pattern) {
        this.pattern = pattern;
    }

    public static Optional<ServiceEnum> find(String input) {
        for (ServiceEnum value : values()) {
            if (value.pattern.matcher(input).find()) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
