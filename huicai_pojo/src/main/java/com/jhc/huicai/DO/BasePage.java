package com.jhc.huicai.DO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author : zhengyue
 *
 **/
@Data
@ApiModel
public class BasePage {
    /**
     * 当前页码
     */
    @ApiModelProperty("当前页码")
    private Integer currentPage=1;

    /**
     * 每页的记录数
     */
    @ApiModelProperty("每页的记录数15")
    private Integer pageSize=15;

}