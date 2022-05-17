package com.jhc.huicai.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author:zeh
 * @Description:权限id
 * @Date: 下午 2021/7/12
 */
@Data
@ApiModel("SysPermissionBanRestart :: 权限删除")
public class SysPermissionBanRestart {

    @ApiModelProperty("权限ID")
    @NotEmpty(message = "权限ID不能为空")
    private List<Long> permissionId;
}