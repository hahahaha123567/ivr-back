package org.ivr.api.dal.ticket.vo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/10
 **/
public interface IRailwayResponseData {

    RailwayResponseVO<? extends IRailwayResponseData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException;
}
