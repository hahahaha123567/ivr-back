package org.ivr.api.dal.ticket.vo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @description query查询出的数据内容
 * @create 2019/04/08
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainVO {

    String trainId;

    String fromStation;

    String toStation;

    LocalTime startTime;

    LocalTime endTime;

    Map<String, String> seatsLeft;
}
