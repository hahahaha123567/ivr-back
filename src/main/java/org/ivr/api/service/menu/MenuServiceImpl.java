package org.ivr.api.service.menu;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.ivr.api.dal.recognition.ServiceEnum;
import org.ivr.api.dal.ticket.enums.SeatTypeEnum;
import org.ivr.api.dal.ticket.po.StationPO;
import org.ivr.api.dal.ticket.po.TrainPO;
import org.ivr.api.dal.ticket.vo.QueryData;
import org.ivr.api.dal.ticket.vo.QueryTicketPriceData;
import org.ivr.api.exception.MissingArgException;
import org.ivr.api.exception.UnrecognizedException;
import org.ivr.api.helper.ThreadLocalHelper;
import org.ivr.api.service.recognition.RecognitionService;
import org.ivr.api.service.reply.ReplyService;
import org.ivr.api.service.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/02
 **/
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuServiceImpl implements MenuService {

    RecognitionService recognitionService;
    ReplyService replyService;
    TicketService ticketService;

    @Autowired
    public MenuServiceImpl(RecognitionService recognitionService, TicketService ticketService, ReplyService replyService) {
        this.recognitionService = recognitionService;
        this.ticketService = ticketService;
        this.replyService = replyService;
    }

    @Override
    public String navigate(String input, Integer requestId) {
        ThreadLocalHelper.setRequestId(requestId);
        Optional<List<StationPO>> stations = recognitionService.twoStation(input);
        Optional<LocalDate> date = recognitionService.date(input);
        Optional<TrainPO> train = recognitionService.train(input);
        Optional<SeatTypeEnum> seatType = recognitionService.seatType(input);
        try {
            switch (recognitionService.service(input).orElse(ServiceEnum.DEFAULT)) {
                case TICKET_QUERY: {
                    QueryData data = ticketService.query(
                            date.orElseThrow(() -> new MissingArgException("请问您想要查询哪天的车票情况?")),
                            stations.orElseThrow(() -> new MissingArgException("请说明您的出发地和目的地")).get(0),
                            stations.get().get(1)
                    );
                    return replyService.reply(data);
                }
                case TICKET_QUERY_PRICE: {
                    QueryTicketPriceData data = ticketService.queryTicketPrice(
                            train.orElseThrow(() -> new MissingArgException("请问您要查询什么车次?")),
                            stations.orElseThrow(() -> new MissingArgException("请说明您的出发地和目的地")).get(0),
                            stations.get().get(1),
                            date.orElseThrow(() -> new MissingArgException("请问您想要查询哪天的车票情况?"))
                    );
                    return replyService.reply(data, seatType.orElseThrow(() -> new MissingArgException("请问您要查询哪种票?")));
                }
                default: {
                    throw new UnrecognizedException("欢迎使用车票查询服务, 请说明您的出发地目的地和出发日期");
                }
            }
        } finally {
            ThreadLocalHelper.removeRequestId();
        }
    }
}
