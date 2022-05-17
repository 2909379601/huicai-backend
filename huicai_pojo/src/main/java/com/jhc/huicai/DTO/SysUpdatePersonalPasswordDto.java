package com.jhc.huicai.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/08/14/15:36
 * @Description:
 */
@Data
public class SysUpdatePersonalPasswordDto {

    @NotBlank(message = "用户编号不能为空")
    private String number;

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty("旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, message = "密码不能少于6位")
    @ApiModelProperty("新密码")
    private String newPassword;
}