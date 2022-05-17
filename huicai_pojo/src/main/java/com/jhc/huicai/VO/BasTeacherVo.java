package com.jhc.huicai.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/07/03/21:29
 * @Description:
 */
@Data
public class BasTeacherVo {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
     private Long id ;

    /**
     * 职工号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 0->默认，1->男，2->女，3->保密
     */
    private Integer sex;

    /**
     * 电话
     */
    private String phone;

    /**
     *子部门编号
     */
    private String deptNumber;

    /**
     *顶级部门编号
     */
    private String topDeptNumber;

    /**
     *子部门名称
     */
    private String deptName;

    /**
     *子顶级部门名称
     */
    private String topDeptName;

}