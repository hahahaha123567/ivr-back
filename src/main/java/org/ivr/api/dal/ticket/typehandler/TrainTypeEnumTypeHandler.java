package org.ivr.api.dal.ticket.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.ivr.api.dal.ticket.enums.TrainTypeEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/10
 **/
public class TrainTypeEnumTypeHandler extends BaseTypeHandler<TrainTypeEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TrainTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public TrainTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String rsString = rs.getString(columnName);
        return TrainTypeEnum.getByCode(rsString)
                .orElseThrow(() -> new RuntimeException(String.format("反序列化失败, 字符串%s", rsString)));
    }

    @Override
    public TrainTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String rsString = rs.getString(columnIndex);
        return TrainTypeEnum.getByCode(rsString)
                .orElseThrow(() -> new RuntimeException(String.format("反序列化失败, 字符串%s", rsString)));
    }

    @Override
    public TrainTypeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String rsString = cs.getString(columnIndex);
        return TrainTypeEnum.getByCode(rsString)
                .orElseThrow(() -> new RuntimeException(String.format("反序列化失败, 字符串%s", rsString)));
    }
}
