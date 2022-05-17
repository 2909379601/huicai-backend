package com.jhc.huicai.DO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 教师档案
 * </p>
 *
 * @author liao
 * @since 2022-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BasTeacher extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 职工号
     */
    private String number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 教师状态
     */
    private Integer status;

    /**
     * 性别 0->默认，1->男，2->女，3->保密
     */
    private Integer sex;

    /**
     * 电话 
     */
    private String phone;

    /**
     * 教师类型
     */
    private Integer type;

    /**
     * 子部门编号
     */
    private String deptNumber;

    /**
     * 顶级部门编号
     */
    private String topDeptNumber;


}