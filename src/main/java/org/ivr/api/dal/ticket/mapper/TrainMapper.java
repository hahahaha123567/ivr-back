package org.ivr.api.dal.ticket.mapper;

import org.apache.ibatis.annotations.Param;
import org.ivr.api.dal.ticket.po.TrainPO;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/10
 **/
@Repository
public interface TrainMapper {

    TrainPO selectByTrainId(@Param("trainId") String trainId);
}
