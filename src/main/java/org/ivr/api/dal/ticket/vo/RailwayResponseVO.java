package org.ivr.api.dal.ticket.vo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author zhangyaoxin@yiwise.com
 * @description 12306接口返回形式
 * @create 2019/04/08
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RailwayResponseVO<T> {

    T data;

    Integer httpstatus;

    String messages;

    Boolean status;

    @Override
    public String toString() {
        return "RailwayResponseVO{" +
                "data=" + data +
                ", httpstatus=" + httpstatus +
                ", messages='" + messages + '\'' +
                ", status=" + status +
                '}';
    }
}
