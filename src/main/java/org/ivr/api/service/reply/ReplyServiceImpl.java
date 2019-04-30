package org.ivr.api.service.reply;

import org.ivr.api.dal.ticket.enums.SeatTypeEnum;
import org.ivr.api.dal.ticket.vo.QueryData;
import org.ivr.api.dal.ticket.vo.QueryTicketPriceData;
import org.ivr.api.dal.ticket.vo.TrainVO;
import org.ivr.api.helper.TimeHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/10
 **/
@Service
public class ReplyServiceImpl implements ReplyService {
    @Override
    public String reply(QueryData data) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        List<TrainVO> results = data.getResult();
        for (TrainVO trainVO : results) {
            int returnTicketNum = 3;
            if (++count > returnTicketNum) {
                break;
            }
            sb.append(String.format("%s号车, %s发车, 还有",
                    trainVO.getTrainId(), TimeHelper.toString(trainVO.getStartTime())));
            for (Map.Entry<String, String> entry : trainVO.getSeatsLeft().entrySet()) {
                sb.append(String.format("%s票、", entry.getKey()));
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(";");
        }
        sb.append(String.format(" 省略了%d个结果", results.size() - count));
        return sb.toString();
    }

    @Override
    public String reply(QueryTicketPriceData data, SeatTypeEnum seatType) {
        String price = data.get(seatType);
        if (price == null) {
            return "您查询的座位类型没有余票, 请查询其他座位";
        } else {
            return String.format("%s%s元", seatType.getName(), price);
        }
    }
}
