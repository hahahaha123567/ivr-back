package org.ivr.api.service.context;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/12
 **/
public interface ContextService {

    String DATE_KEY = "DATE_";
    String TWO_STATION_KEY = "TWO_STATION_";
    String SEAT_TYPE_KEY = "SEAT_TYPE_";
    String TRAIN_KEY = "TRAIN_";

    String SERVICE_KEY = "SERVICE_";
    String SERVICE_RESULT_KEY = "SERVICE_RESULT_";

    <T> void set(String property, T value);

    <T> T get(String key);
}
