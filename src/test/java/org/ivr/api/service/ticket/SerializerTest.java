package org.ivr.api.service.ticket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ivr.api.dal.ticket.vo.QueryByTrainNoData;
import org.ivr.api.dal.ticket.vo.QueryData;
import org.ivr.api.dal.ticket.vo.QueryTicketPriceData;
import org.ivr.api.dal.ticket.vo.RailwayResponseVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/08
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SerializerTest {

    @Autowired
    private Gson gson;

    private String queryStr;
    private String queryByTrainNoStr;
    private String queryTicketPriceStr;

    @Before
    public void setUp() {
        queryStr = "{\"data\":{\"flag\": \"1\",\"map\": {\"HGH\": \"杭州东\",\"HZH\": \"杭州\",\"TIP\": \"天津南\",\"TJP\": \"天津\",\"TXP\": \"天津西\",\"YQP\": \"杨柳青\"},\"result\": [\"|预订|5u00000G380I|G38|NXG|VNP|HGH|TIP|11:34|17:26|05:52|N|FwLpW1ET%2FpxYskn6orG5ip17Hf%2BSQemZG5zUx3RNcZrzdcMq|20190418|3|G1|08|19|1|0|||||||||||无|无|无||O0M090|OM9|0|0|null\",\"a4P%2BzNLLFv22vtGffh9XyINuVcFqp6ayF1bD6UIO%2FE61F4W42xJshoBUJ7TfOgDDc53P81V9lnHb%0AD7RxpgXtgEYM4lBEL3Xk%2B%2FAmRPCIG%2BfTJLfIvIP2SrA4mEWeQHpwikG9IAK278NXX0vDwQ6zpO1r%0A5uYrOnfN7SQjNwMAaO%2B1ZJd3edpyYu14l8rk7Lj3E7zs5SQlQeXtOHgIxlahz9Os7A1k5bKK%2BUha%0Ac0XZlZneC8yI5lp5em4p66ZmnwCqk9c780X1IfYd2pBMh0nVXxxs4nLoix4W3VMKEZJzNKGss3iY%0A|预订|5j0000G16822|G168|VRH|VNP|HGH|TIP|11:39|17:37|05:58|Y|g9q5QK9fyNNPfD%2FveWJV9q0ht9pUGvp1DZupe%2FcWeEGQyDdS|20190418|3|H6|09|23|1|0|||||||||||有|16|2||O0M090|OM9|0|0|null\",\"|预订|5800000G560J|G56|FZS|VNP|HGH|TIP|12:47|18:15|05:28|N|FwLpW1ET%2FpxYskn6orG5ip17Hf%2BSQemZG5zUx3RNcZrzdcMq|20190418|3|G1|10|19|1|0|||||||||||无|无|无||O0M090|OM9|0|0|null\",\"|预订|5a0000K552C2|K552|RZH|MDB|HZH|YQP|20:12|14:44|18:32|Y|5cJG18L%2BL7lpniWn2WgCjpTvtUlOQVf9HWbdxSSNkLLld8BkCBKU9nN%2BS%2FQ%3D|20190418|3|H3|08|29|0|0||||无|||20||有|有|||||10401030|1413|0|0|null\"]},\"httpstatus\": 200,\"messages\": \"\",\"status\": true}";
        queryByTrainNoStr = "{\"validateMessagesShowId\":\"_validatorMessage\",\"status\":true,\"httpstatus\":200,\"data\":{\"data\":[{\"start_station_name\":\"深圳北\",\"arrive_time\":\"----\",\"station_train_code\":\"D906\",\"station_name\":\"深圳北\",\"train_class_name\":\"动车\",\"service_type\":\"1\",\"start_time\":\"20:04\",\"stopover_time\":\"----\",\"end_station_name\":\"上海\",\"station_no\":\"01\",\"isEnabled\":false},{\"arrive_time\":\"05:44\",\"station_name\":\"杭州东\",\"start_time\":\"05:50\",\"stopover_time\":\"6分钟\",\"station_no\":\"02\",\"isEnabled\":true},{\"arrive_time\":\"06:40\",\"station_name\":\"上海虹桥\",\"start_time\":\"06:43\",\"stopover_time\":\"3分钟\",\"station_no\":\"03\",\"isEnabled\":true},{\"arrive_time\":\"07:12\",\"station_name\":\"上海\",\"start_time\":\"07:12\",\"stopover_time\":\"----\",\"station_no\":\"04\",\"isEnabled\":true}]},\"messages\":[],\"validateMessages\":{}}";
        queryTicketPriceStr = "{\"data\":{\"1\":\"1805\",\"3\":\"3105\",\"4\":\"4885\",\"A1\":\"¥180.5\",\"A3\":\"¥310.5\",\"A4\":\"¥488.5\",\"OT\":[],\"WZ\":\"¥180.5\",\"train_no\":\"560000Z28230\"},\"httpstatus\":200,\"messages\":\"\",\"status\":true}";
    }

    @Test
    public void query() {
        RailwayResponseVO query = gson.fromJson(queryStr, new TypeToken<RailwayResponseVO<QueryData>>() {
        }.getType());
        System.out.println(query);
    }

    @Test
    public void queryByTrainNo() {
        RailwayResponseVO query = gson.fromJson(queryByTrainNoStr, new TypeToken<RailwayResponseVO<QueryByTrainNoData>>() {
        }.getType());
        System.out.println(query);
    }

    @Test
    public void QueryTicketPriceData() {
        RailwayResponseVO query = gson.fromJson(queryTicketPriceStr, new TypeToken<RailwayResponseVO<QueryTicketPriceData>>() {
        }.getType());
        System.out.println(query);
    }
}
