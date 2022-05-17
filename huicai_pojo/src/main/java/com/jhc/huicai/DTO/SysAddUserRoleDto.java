package com.jhc.huicai.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zeh
 */
@Data
@ApiModel("SysAddUserRoleDto :: 给用户添加角色")
public class SysAddUserRoleDto {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String number;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private List<String> authData;


}