package com.jhc.huicai.DTO;

import com.jhc.huicai.DO.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/07/03/15:19
 * @Description: 模糊查询实体类
 */
@Data
public class BasStudentDto extends BasePage {
    private static final long serialVersionUID=1L;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String number;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;


    /**
     * 学院编号
     */
    @ApiModelProperty("学院编号")
    private String collegeNumber;

    /**
     * 专业编号
     */
    @ApiModelProperty("专业编号")
    private String majorNumber;

}