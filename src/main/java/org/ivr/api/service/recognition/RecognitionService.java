package org.ivr.api.service.recognition;

import org.ivr.api.dal.recognition.ServiceEnum;
import org.ivr.api.dal.ticket.enums.SeatTypeEnum;
import org.ivr.api.dal.ticket.po.StationPO;
import org.ivr.api.dal.ticket.po.TrainPO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/11
 **/
public interface RecognitionService {

    /**
     * 识别服务内容
     *
     * @param input 文本
     * @return 服务类型
     */
    Optional<ServiceEnum> service(String input);

    /**
     * 识别日期参数(今天, 后天, 12月10号, 12月10日, 10号, 周日, 下周一)
     *
     * @param input 文本
     * @return 日期
     */
    Optional<LocalDate> date(String input);

    /**
     * 两个车站
     *
     * @param input 文本
     * @return 两个车站的list
     */
    Optional<List<StationPO>> twoStation(String input);

    /**
     * 车票类型(座位)
     *
     * @param input 文本
     * @return 座位类型
     */
    Optional<SeatTypeEnum> seatType(String input);

    /**
     * 车次
     *
     * @param input 文本
     * @return 车次
     */
    Optional<TrainPO> train(String input);
}
