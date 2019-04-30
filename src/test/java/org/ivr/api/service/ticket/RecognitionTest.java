package org.ivr.api.service.ticket;

import org.ivr.api.dal.ticket.enums.SeatTypeEnum;
import org.ivr.api.dal.ticket.po.StationPO;
import org.ivr.api.dal.ticket.po.TrainPO;
import org.ivr.api.service.recognition.RecognitionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/11
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RecognitionTest {

    @Autowired
    private RecognitionService recognitionService;

    @Test
    public void date1() {
        String input = "1月31日";
        Optional<LocalDate> date = recognitionService.date(input);
        System.out.println(date.orElse(null));
    }

    @Test
    public void date2() {
        String input = "1月32日";
        Optional<LocalDate> date = recognitionService.date(input);
        System.out.println(date.orElse(null));
    }

    @Test
    public void date3() {
        String input = "今天";
        Optional<LocalDate> date = recognitionService.date(input);
        System.out.println(date.orElse(null));
    }

    @Test
    public void date4() {
        String input = "周日";
        Optional<LocalDate> date = recognitionService.date(input);
        System.out.println(date.orElse(null));

    }

    @Test
    public void date5() {
        String input = "下周日";
        Optional<LocalDate> date = recognitionService.date(input);
        System.out.println(date.orElse(null));
    }

    @Test
    public void date6() {
        String input = "20号";
        Optional<LocalDate> date = recognitionService.date(input);
        System.out.println(date.orElse(null));
    }

    @Test
    public void date7() {
        String input = "3月";
        Optional<LocalDate> date = recognitionService.date(input);
        assertEquals(date, Optional.empty());
    }

    @Test
    public void twoStation1() {
        String input = "从杭州东到北京的车票";
        Optional<List<StationPO>> stationPOList = recognitionService.twoStation(input);
        stationPOList.orElseGet(ArrayList::new).forEach(System.out::println);
    }

    @Test
    public void seatType1() {
        String input = "二等座";
        Optional<SeatTypeEnum> seatType = recognitionService.seatType(input);
        System.out.println(seatType);
        assertEquals(seatType.orElse(null), SeatTypeEnum.SEAT3);
    }

    @Test
    public void train1() {
        String input = "哈哈dd123";
        Optional<TrainPO> train = recognitionService.train(input);
        System.out.println(train.orElse(null));
    }
}
