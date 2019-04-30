package org.ivr.api.dal.ticket.po;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.ivr.api.dal.ticket.enums.TrainTypeEnum;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/04
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainPO {

    String trainId;

    Long fromStationId;

    Long toStationId;

    TrainTypeEnum trainType;

    String code;
}
