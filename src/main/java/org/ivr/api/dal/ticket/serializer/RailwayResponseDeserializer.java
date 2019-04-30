package org.ivr.api.dal.ticket.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.ivr.api.dal.ticket.vo.IRailwayResponseData;
import org.ivr.api.dal.ticket.vo.RailwayResponseVO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/08
 **/
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RailwayResponseDeserializer implements JsonDeserializer<RailwayResponseVO<? extends IRailwayResponseData>> {

    @Override
    public RailwayResponseVO<? extends IRailwayResponseData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Type innerType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
        Class innerClass = (Class) innerType;
        Object o = null;
        try {
            o = innerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (o instanceof IRailwayResponseData) {
            IRailwayResponseData oo = (IRailwayResponseData) o;
            return oo.deserialize(json, typeOfT, context);
        } else {
            throw new RuntimeException(String.format("反序列化失败, type = %s", typeOfT.getTypeName()));
        }
    }
}
