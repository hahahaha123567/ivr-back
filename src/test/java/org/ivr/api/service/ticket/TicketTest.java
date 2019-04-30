package org.ivr.api.service.ticket;

import org.ivr.api.dal.ticket.enums.PurposeCodeEnum;
import org.ivr.api.dal.ticket.mapper.StationMapper;
import org.ivr.api.dal.ticket.mapper.TrainMapper;
import org.ivr.api.dal.ticket.po.StationPO;
import org.ivr.api.dal.ticket.po.TrainPO;
import org.ivr.api.dal.ticket.vo.QueryData;
import org.ivr.api.dal.ticket.vo.QueryTicketPriceData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/08
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TicketTest {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private TrainMapper trainMapper;

    private StationPO hangzhou;
    private StationPO tianjin;
    private TrainPO train;
    private LocalDate tomorrow;

    @Before
    public void setUp() {
        hangzhou = stationMapper.selectByName("杭州东");
        tianjin = stationMapper.selectByName("天津西");
        train = trainMapper.selectByTrainId("D718");
        tomorrow = LocalDate.now().plusDays(1);
    }

    @Test
    public void query1() {
        QueryData queryData = ticketService.query(tomorrow, hangzhou, tianjin);
        System.out.println(queryData);
    }

    @Test
    public void query2() {
        QueryData queryData = ticketService.query(tomorrow, hangzhou, tianjin, PurposeCodeEnum.STUDENT);
        System.out.println(queryData);
    }

    @Test
    public void queryTicketPrice1() {
        QueryTicketPriceData queryTicketPriceData = ticketService.queryTicketPrice(train, hangzhou, tianjin, tomorrow);
        System.out.println(queryTicketPriceData);
    }
}
