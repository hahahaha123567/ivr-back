package org.ivr.api.service.ticket;

import org.ivr.api.dal.ticket.enums.PurposeCodeEnum;
import org.ivr.api.dal.ticket.po.StationPO;
import org.ivr.api.dal.ticket.po.TrainPO;
import org.ivr.api.dal.ticket.vo.QueryData;
import org.ivr.api.dal.ticket.vo.QueryTicketPriceData;

import java.time.LocalDate;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/04
 **/
public interface TicketService {

    /**
     * 查询剩余车票
     *
     * @param trainDate   发车日期
     * @param fromStation 出发站
     * @param toStation   目标站
     * @return 查询结果的自然语言
     */
    QueryData query(LocalDate trainDate, StationPO fromStation, StationPO toStation);

    /**
     * 查询剩余车票
     *
     * @param trainDate   发车日期
     * @param fromStation 出发站
     * @param toStation   目标站
     * @param purposeCode 车票类型
     * @return 查询结果的自然语言
     */
    QueryData query(LocalDate trainDate, StationPO fromStation, StationPO toStation, PurposeCodeEnum purposeCode);

    /**
     * 查询票价
     *
     * @param train        车次code
     * @param fromStation 出发站
     * @param toStation   目标站
     * @param trainDate   发车日期
     * @return 查询结果的自然语言
     */
    QueryTicketPriceData queryTicketPrice(TrainPO train, StationPO fromStation, StationPO toStation, LocalDate trainDate);
}
