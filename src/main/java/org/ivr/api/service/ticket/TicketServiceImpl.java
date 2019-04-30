package org.ivr.api.service.ticket;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.ivr.api.dal.ticket.enums.PurposeCodeEnum;
import org.ivr.api.dal.ticket.mapper.StationMapper;
import org.ivr.api.dal.ticket.mapper.TrainMapper;
import org.ivr.api.dal.ticket.po.StationPO;
import org.ivr.api.dal.ticket.po.TrainPO;
import org.ivr.api.dal.ticket.vo.QueryByTrainNoData;
import org.ivr.api.dal.ticket.vo.QueryData;
import org.ivr.api.dal.ticket.vo.QueryTicketPriceData;
import org.ivr.api.dal.ticket.vo.RailwayResponseVO;
import org.ivr.api.helper.HttpHelper;
import org.ivr.api.helper.TimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/04
 **/
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketServiceImpl implements TicketService {

    @Value("${ticket.query.url}")
    String queryUrl;
    @Value("${ticket.queryByTrainNo.url}")
    String queryByTrainNoUrl;
    @Value("${ticket.queryTicketPrice.url}")
    String queryTicketPriceUrl;

    Gson gson;
    TrainMapper trainMapper;
    TicketService ticketService;

    @Autowired
    public TicketServiceImpl(Gson gson, @Lazy TicketService ticketService, StationMapper stationMapper, TrainMapper trainMapper) {
        this.gson = gson;
        this.ticketService = ticketService;
        this.trainMapper = trainMapper;
    }

    @Override
    public QueryData query(LocalDate trainDate, StationPO fromStation, StationPO toStation) {
        return ticketService.query(trainDate, fromStation, toStation, PurposeCodeEnum.ADULT);
    }

    @Override
    public QueryData query(LocalDate trainDate, StationPO fromStation, StationPO toStation, PurposeCodeEnum purposeCode) {
        String requestUrl = HttpHelper.concatUrl(queryUrl, ImmutableMap.of(
                "leftTicketDTO.train_date", TimeHelper.toString(trainDate),
                "leftTicketDTO.from_station", fromStation.getCode(),
                "leftTicketDTO.to_station", toStation.getCode(),
                "purpose_codes", purposeCode.getCode()
        ));
        String response = HttpHelper.get(requestUrl);
        RailwayResponseVO<QueryData> responseVO = gson.fromJson(response, new TypeToken<RailwayResponseVO<QueryData>>() {
        }.getType());
        if (responseVO == null) {
            throw new RuntimeException("查询出错, 反序列化结果失败");
        }
        return responseVO.getData();
    }

    @Override
    public QueryTicketPriceData queryTicketPrice(TrainPO train, StationPO fromStation, StationPO toStation, LocalDate trainDate) {
        // query train info to get station_no
        String requestUrl = HttpHelper.concatUrl(queryByTrainNoUrl, ImmutableMap.of(
                "train_no", train.getCode(),
                "from_station_telecode", fromStation.getCode(),
                "to_station_telecode", toStation.getCode(),
                "depart_date", TimeHelper.toString(trainDate)
        ));
        String response = HttpHelper.get(requestUrl);
        RailwayResponseVO<QueryByTrainNoData> response1 = gson.fromJson(response, new TypeToken<RailwayResponseVO<QueryByTrainNoData>>() {
        }.getType());
        Assert.notNull(response1, String.format("查询出错, 反序列化结果失败, response = %s", response));
        // station name -> station no
        Map<String, String> map = new HashMap<>();
        response1.getData().getData().forEach(aMap -> map.put(aMap.get("station_name"), aMap.get("station_no")));
        //
        requestUrl = HttpHelper.concatUrl(queryTicketPriceUrl, ImmutableMap.of(
                "train_no", train.getCode(),
                "from_station_no", fuzzyGet(map, fromStation.getName()),
                "to_station_no", fuzzyGet(map, toStation.getName()),
                "seat_types", train.getTrainType().getPriceParam(),
                "train_date", TimeHelper.toString(trainDate)
        ));
        response = HttpHelper.get(requestUrl);
        RailwayResponseVO<QueryTicketPriceData> response2 = gson.fromJson(response, new TypeToken<RailwayResponseVO<QueryTicketPriceData>>() {
        }.getType());
        Assert.notNull(response2, String.format("查询出错, 反序列化结果失败, response = %s", response));
        return response2.getData();
    }

    private <T> T fuzzyGet(Map<String, T> map, String key) {
        for (String s : map.keySet()) {
            if (s.contains(key)) {
                return map.get(s);
            }
        }
        throw new RuntimeException("查询车次查找对应站点出错");
    }
}
