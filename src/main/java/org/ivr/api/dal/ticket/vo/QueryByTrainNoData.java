package org.ivr.api.dal.ticket.vo;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/10
 **/
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryByTrainNoData implements IRailwayResponseData {

    List<Map<String, String>> data;

    @Override
    public RailwayResponseVO<? extends IRailwayResponseData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RailwayResponseVO<QueryByTrainNoData> response = new RailwayResponseVO<>();
        JsonObject responseMap = json.getAsJsonObject();
        response.setHttpstatus(responseMap.get("httpstatus").getAsInt());
        response.setMessages(responseMap.get("messages").getAsJsonArray().toString());
        response.setStatus(responseMap.get("status").getAsBoolean());
        JsonObject dataMap = responseMap.get("data").getAsJsonObject();
        QueryByTrainNoData queryData = new QueryByTrainNoData();
        List<Map<String, String>> queryDataList = new ArrayList<>();
        JsonArray data = dataMap.get("data").getAsJsonArray();
        for (JsonElement jsonElement : data) {
            JsonObject item = jsonElement.getAsJsonObject();
            Map<String, String> map = ImmutableMap.of(
                    "arrive_time", item.get("arrive_time").getAsString(),
                    "start_time", item.get("start_time").getAsString(),
                    "station_name", item.get("station_name").getAsString(),
                    "station_no", item.get("station_no").getAsString()
            );
            queryDataList.add(map);
        }
        queryData.setData(queryDataList);
        response.setData(queryData);
        return response;
    }
}
