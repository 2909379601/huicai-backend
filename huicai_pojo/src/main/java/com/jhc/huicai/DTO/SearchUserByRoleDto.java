package com.jhc.huicai.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/10/20/14:43
 * @Description:
 */
@Data
public class SearchUserByRoleDto {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "角色id")
    @NotNull(message = "角色ID不得为空")
    private String roleId;

    @ApiModelProperty(value = "姓名")
    private String name;

//    @JsonSerialize(using = ToStringSerializer.class)
//    @ApiModelProperty(value = "用户id")
//    private Long id;

    @ApiModelProperty(value = "用户编号")
    private String number;

    @ApiModelProperty(value = "是否属于这个角色id")
    private Boolean isBelong;

}