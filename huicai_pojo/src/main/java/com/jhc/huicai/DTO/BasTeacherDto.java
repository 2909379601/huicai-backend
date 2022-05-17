package com.jhc.huicai.DTO;

import com.jhc.huicai.DO.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/07/03/21:28
 * @Description:
 */
@Data
public class BasTeacherDto extends BasePage {


    /**
     * 职工号
     */
    @ApiModelProperty("职工号")
    private String number;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 性别 0->默认，1->男，2->女，3->保密
     */
    @ApiModelProperty("性别")
    private Integer sex;



    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;

    /**
     *子部门编号
     */
    @ApiModelProperty("子部门编号")
    private String deptNumber;

    /**
     *顶级部门编号
     */
    @ApiModelProperty("顶级部门编号")
    private String topDeptNumber;

}