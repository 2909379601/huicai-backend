package com.jhc.huicai.VO;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/07/03/15:35
 * @Description:
 */
@Data
public class BasStudentVo {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 学号
     */
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
     * 年级
     */
    private String grade;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 专业编号
     */
    private String majorNumber;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 学院编号
     */
    private String collegeNumber;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 电话
     */
    private String phone;

}