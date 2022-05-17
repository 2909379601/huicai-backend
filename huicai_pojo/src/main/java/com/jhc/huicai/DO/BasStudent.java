package com.jhc.huicai.DO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生档案
 * </p>
 *
 * @author liao
 * @since 2022-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BasStudent extends BaseDo {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */

    private String number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 启停状态 1->启用 0->禁用
     */
    private Boolean isEnable;

    /**
     * 性别 0->默认，1->男，2->女，3->保密
     */
    private Integer sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 年级
     */
    private String grade;

    /**
     * 学院编号
     */
    private String collegeNumber;

    /**
     * 班级编号
     */
    private String classNumber;

    /**
     * 专业编号
     */
    private String majorNumber;


}