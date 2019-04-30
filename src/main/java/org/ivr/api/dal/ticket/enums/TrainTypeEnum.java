package org.ivr.api.dal.ticket.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author zhangyaoxin@yiwise.com
 * @description 列车类型
 * @create 2019/04/04
 **/
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum TrainTypeEnum {

    //
    CHENGJI("C", "城际高速", ""),
    DONGCHE("D", "动车", "OIJ"),
    GAOTIE("G", "高铁", "OM9"),
    KUAI("K", "普快", "134"),
    TEKUAI("T", "特快", "1346"),
    ZHIDA("Z", "直达", "134"),
    OTHERS("O", "其他", ""),
    ;

    @Getter
    String code;

    String info;

    @Getter
    String priceParam;

    public static Optional<TrainTypeEnum> getByCode(String code) {
        Assert.notNull(code, "查询列车类型使用的code为空");
        for (TrainTypeEnum trainType : values()) {
            if (trainType.code.equals(code)) {
                return Optional.of(trainType);
            }
        }
        return Optional.empty();
    }

}
