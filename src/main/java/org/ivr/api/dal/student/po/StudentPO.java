package org.ivr.api.dal.student.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.ivr.api.dal.student.enums.GenderEnum;

/**
 * @author zhangyaoxin@yiwise.com
 * @create 2019/04/02
 **/
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentPO {

    Long studentId;

    String name;

    GenderEnum gender;
}
