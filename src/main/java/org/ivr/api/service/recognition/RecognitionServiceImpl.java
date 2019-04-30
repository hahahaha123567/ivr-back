package org.ivr.api.service.recognition;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.ivr.api.aop.ContextAnnotation;
import org.ivr.api.dal.recognition.ServiceEnum;
import org.ivr.api.dal.ticket.enums.SeatTypeEnum;
import org.ivr.api.dal.ticket.mapper.StationMapper;
import org.ivr.api.dal.ticket.mapper.TrainMapper;
import org.ivr.api.dal.ticket.po.StationPO;
import org.ivr.api.dal.ticket.po.TrainPO;
import org.ivr.api.service.context.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/11
 **/
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecognitionServiceImpl implements RecognitionService {

    private static List<String> stationList;
    private static Map<String, Integer> dateMap;
    private static Map<String, Integer> weekMap;
    private static Pattern dayPattern = Pattern.compile("\\W(\\d+)[日号]");
    private static Pattern monthDayPattern = Pattern.compile("(\\d+)月(\\d+)[日号]");
    private static Pattern trainPattern = Pattern.compile("[a-z]\\d+");

    static {
        dateMap = new HashMap<>(7);
        dateMap.put("大前天", -3);
        dateMap.put("前天", -2);
        dateMap.put("昨天", -1);
        dateMap.put("今天", 0);
        dateMap.put("明天", 1);
        dateMap.put("后天", 2);
        dateMap.put("大后天", 3);
        weekMap = new HashMap<>(7);
        weekMap.put("周一", 1);
        weekMap.put("周二", 2);
        weekMap.put("周三", 3);
        weekMap.put("周四", 4);
        weekMap.put("周五", 5);
        weekMap.put("周六", 6);
        weekMap.put("周日", 7);
    }

    StationMapper stationMapper;
    TrainMapper trainMapper;

    @Autowired
    public RecognitionServiceImpl(StationMapper stationMapper, TrainMapper trainMapper) {
        this.stationMapper = stationMapper;
        this.trainMapper = trainMapper;
        stationList = stationMapper.selectAllNames();
    }

    @Override
    public Optional<ServiceEnum> service(String input) {
        return ServiceEnum.find(input);
    }

    @Override
    @ContextAnnotation(property = ContextService.DATE_KEY)
    public Optional<LocalDate> date(String input) {
        LocalDate now = LocalDate.now();
        for (Map.Entry<String, Integer> entry : dateMap.entrySet()) {
            if (input.contains(entry.getKey())) {
                return Optional.of(now.plusDays(entry.getValue()));
            }
        }
        for (Map.Entry<String, Integer> entry : weekMap.entrySet()) {
            if (input.contains(entry.getKey())) {
                int plusDays = entry.getValue() - now.getDayOfWeek().getValue();
                if (input.contains("下" + entry.getKey())) {
                    plusDays += 7;
                }
                return Optional.of(now.plusDays(plusDays));
            }
        }
        Matcher matcher1 = monthDayPattern.matcher(input);
        if (matcher1.find()) {
            LocalDate date = null;
            try {
                date = LocalDate.of(now.getYear(), Integer.parseInt(matcher1.group(1)), Integer.parseInt(matcher1.group(2)));
            } catch (DateTimeException ignore) {
            }
            return Optional.ofNullable(date);
        }
        Matcher matcher2 = dayPattern.matcher(input);
        if (matcher2.find()) {
            LocalDate date = null;
            try {
                date = LocalDate.of(now.getYear(), now.getMonth(), Integer.parseInt(matcher2.group(1)));
            } catch (DateTimeException ignore) {
            }
            return Optional.ofNullable(date);
        }
        return Optional.empty();
    }

    @Override
    @ContextAnnotation(property = ContextService.TWO_STATION_KEY)
    public Optional<List<StationPO>> twoStation(String input) {
        int i = 0, j = 1;
        String tmp;
        String first = null, second = null;
        while (j < input.length()) {
            tmp = input.substring(i, j);
            Optional<String> stationName = stationList.stream().filter(tmp::contains).findAny();
            if (stationName.isPresent()) {
                first = stationName.get();
                break;
            }
            j++;
        }
        if (first == null) {
            return Optional.empty();
        }
        i = j;
        j = i + 1;
        while (j <= input.length()) {
            tmp = input.substring(i, j);
            Optional<String> stationName = stationList.stream().filter(tmp::contains).findAny();
            if (stationName.isPresent()) {
                second = stationName.get();
                break;
            }
            j++;
        }
        if (second == null) {
            return Optional.empty();
        }
        return Optional.of(Arrays.asList(stationMapper.selectByName(first), stationMapper.selectByName(second)));
    }

    @Override
    @ContextAnnotation(property = ContextService.SEAT_TYPE_KEY)
    public Optional<SeatTypeEnum> seatType(String input) {
        return SeatTypeEnum.getByPattern(input);
    }

    @Override
    @ContextAnnotation(property = ContextService.TRAIN_KEY)
    public Optional<TrainPO> train(String input) {
        Matcher matcher = trainPattern.matcher(input);
        if (matcher.find()) {
            String trainId = matcher.group();
            trainId = trainId.toUpperCase();
            TrainPO trainPO = trainMapper.selectByTrainId(trainId);
            return trainPO == null ? Optional.empty() : Optional.of(trainPO);
        }
        return Optional.empty();
    }
}
