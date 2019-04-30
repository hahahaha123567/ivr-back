package org.ivr.api.service.reply;

import org.ivr.api.dal.ticket.enums.SeatTypeEnum;
import org.ivr.api.dal.ticket.vo.QueryData;
import org.ivr.api.dal.ticket.vo.QueryTicketPriceData;

/**
 * @author zhangyaoxin@yiwise.com
 * @description 将业务信息转换为回复用户的自然语言
 * @create 2019/04/10
 **/
public interface ReplyService {

    /**
     * 剩余车票
     *
     * @param data 剩余车票
     * @return 剩余车票
     */
    String reply(QueryData data);

    /**
     * 票价
     *
     * @param data     票价
     * @param seatType 查询的座位
     * @return 票价
     */
    String reply(QueryTicketPriceData data, SeatTypeEnum seatType);
}
