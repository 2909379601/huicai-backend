package com.jhc.huicai.DTO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author  zeh
 */
@Data
@ApiModel("SysRoleAddDto :: 角色添加")
public class SysRoleAddDto {

    /**
     * 角色中文名称 角色中文名称
     */
    @ApiModelProperty("角色中文名称")
    @NotBlank(message = "角色名称不为空")
    private String name;

    /**
     * 角色说明
     */
    private String remark;

}