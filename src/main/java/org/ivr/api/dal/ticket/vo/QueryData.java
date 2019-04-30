package org.ivr.api.dal.ticket.vo;

import com.google.common.collect.Maps;
import com.google.gson.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.ivr.api.dal.ticket.enums.SeatTypeEnum;
import org.ivr.api.helper.TimeHelper;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.*;

/**
 * @author zhangyaoxin@yiwise.com
 * @description query接口的response中data格式
 * @create 2019/04/08
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryData implements IRailwayResponseData {

    private static final String USEFUL_PREFIX = "|预订|";
    String flag;
    Map<String, String> map;
    List<TrainVO> result;

    private static Optional<TrainVO> resolveTrainVO(@Nonnull String result) {
        TrainVO trainVO = new TrainVO();
        // 过滤这个加密的不知道有什么用的前缀
        result = result.substring(result.indexOf(USEFUL_PREFIX));
        String[] info = result.split("\\|");
        //
        trainVO.setTrainId(info[3]);
        trainVO.setFromStation(info[6]);
        trainVO.setToStation(info[7]);
        trainVO.setStartTime(LocalTime.parse(info[10], TimeHelper.getTimeFormatter()));
        trainVO.setEndTime(LocalTime.parse(info[9], TimeHelper.getTimeFormatter()));
        // 剩余座位数
        Map<String, String> seatsLeft = new HashMap<>(7);
        for (SeatTypeEnum seatType : SeatTypeEnum.values()) {
            putIfHasLeft(seatsLeft, seatType.getName(), info[seatType.getFieldNum()]);
        }
        if (seatsLeft.entrySet().size() > 0) {
            trainVO.setSeatsLeft(seatsLeft);
            return Optional.of(trainVO);
        } else {
            return Optional.empty();
        }
    }

    private static void putIfHasLeft(@Nonnull Map<String, String> map, @Nonnull String seat, @Nonnull String left) {
        if (Objects.equals(left, "") || Objects.equals(left, "无")) {
            return;
        }
        map.put(seat, left);
    }

    @Override
    public RailwayResponseVO<QueryData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RailwayResponseVO<QueryData> response = new RailwayResponseVO<>();
        JsonObject responseMap = json.getAsJsonObject();
        response.setHttpstatus(responseMap.get("httpstatus").getAsInt());
        response.setMessages(responseMap.get("messages").getAsString());
        response.setStatus(responseMap.get("status").getAsBoolean());
        JsonObject dataMap = responseMap.get("data").getAsJsonObject();
        QueryData queryData = new QueryData();
        queryData.setFlag(dataMap.get("flag").getAsString());
        JsonObject map = dataMap.get("map").getAsJsonObject();
        queryData.setMap(Maps.asMap(map.keySet(), key -> map.get(key).getAsString()));
        JsonArray results = dataMap.get("result").getAsJsonArray();
        List<TrainVO> trainVOList = new ArrayList<>();
        results.forEach(result -> resolveTrainVO(result.getAsString()).ifPresent(trainVOList::add));
        queryData.setResult(trainVOList);
        queryData.getResult().forEach(trainVO -> trainVO.setFromStation(map.get(trainVO.getFromStation()).getAsString()));
        queryData.getResult().forEach(trainVO -> trainVO.setToStation(map.get(trainVO.getToStation()).getAsString()));
        response.setData(queryData);
        return response;
    }
}
