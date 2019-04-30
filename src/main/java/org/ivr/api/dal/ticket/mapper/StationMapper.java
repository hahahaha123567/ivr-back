package org.ivr.api.dal.ticket.mapper;

import org.apache.ibatis.annotations.Param;
import org.ivr.api.dal.ticket.po.StationPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/10
 **/
@Repository
public interface StationMapper {

    /**
     * 按车站名精确查询
     *
     * @param name 车站名
     * @return 车站列表
     */
    StationPO selectByName(@Param("name") String name);

    /**
     * 查询所有车站名
     *
     * @return 站名列表
     */
    List<String> selectAllNames();
}
