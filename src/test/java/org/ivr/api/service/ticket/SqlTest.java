package org.ivr.api.service.ticket;

import org.ivr.api.dal.ticket.mapper.StationMapper;
import org.ivr.api.dal.ticket.mapper.TrainMapper;
import org.ivr.api.dal.ticket.po.StationPO;
import org.ivr.api.dal.ticket.po.TrainPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/10
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SqlTest {

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private TrainMapper trainMapper;

    @Test
    public void stationMapper1() {
        StationPO station = stationMapper.selectByName("杭州东");
        System.out.println(station);
    }

    @Test
    public void stationMapper2() {
        List<String> names = stationMapper.selectAllNames();
        names.forEach(System.out::println);
    }

    @Test
    public void trainMapper1() {
        TrainPO trainPO = trainMapper.selectByTrainId("D1");
        System.out.println(trainPO);
    }
}
