package org.ivr.api.dal.ticket.vo;

import com.google.gson.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.ivr.api.dal.ticket.enums.SeatTypeEnum;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/09
 **/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryTicketPriceData extends HashMap<SeatTypeEnum, String> implements IRailwayResponseData {

    @Override
    public RailwayResponseVO<? extends IRailwayResponseData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RailwayResponseVO<QueryTicketPriceData> response = new RailwayResponseVO<>();
        JsonObject responseMap = json.getAsJsonObject();
        response.setHttpstatus(responseMap.get("httpstatus").getAsInt());
        JsonElement messages = responseMap.get("messages");
        if (messages instanceof JsonArray) {
            response.setMessages(responseMap.get("messages").getAsJsonArray().toString());
        } else {
            response.setMessages(responseMap.get("messages").getAsString());
        }
        response.setStatus(responseMap.get("status").getAsBoolean());
        JsonObject dataMap = responseMap.get("data").getAsJsonObject();
        QueryTicketPriceData queryData = new QueryTicketPriceData();
        for (Map.Entry<String, JsonElement> entry : dataMap.entrySet()) {
            SeatTypeEnum.getByPriceMark(entry.getKey())
                    .ifPresent(seatType -> queryData.put(seatType, entry.getValue().getAsString()));
        }
        response.setData(queryData);
        return response;
    }
}
