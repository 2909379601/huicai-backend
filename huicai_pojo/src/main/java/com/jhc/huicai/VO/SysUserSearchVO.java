package com.jhc.huicai.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:42
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@Data
public class SysUserSearchVO {
    /**
     * 用户id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 用户编号
     */
    private String number;

    /**
     * 姓名
     */
    private String name;
}