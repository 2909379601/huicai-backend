package com.jhc.huicai.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author:chenxiaotian
 * @Description:权限添加
 * @Date: 9:33 上午 2020/5/19
 */
@Data
@ApiModel("SysUserPermissionAdd :: 用户权限添加")
public class SysUserPermissionAdd {

    @NotNull(message = "用户ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("权限码")
    @NotNull(message = "权限码不能为空")
    private List<String> authList;

    /**
     * 是否马上生效
     */
    @ApiModelProperty("是否马上生效")
    private Boolean ifFast = false;
}