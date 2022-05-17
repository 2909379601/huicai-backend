package com.jhc.huicai.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:36
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@Data
@ApiModel("SysAdminLoginDto :: 用户登录")
public class SysUserLoginDto {

    /**
     * 帐号
     */
    @ApiModelProperty("帐号")
    @NotBlank(message = "帐号不能为空")
    private String number;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}