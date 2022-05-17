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
@ApiModel("SysRoleUpdateDto :: 角色添加")
public class SysRoleUpdateDto {

    /**
     *角色id
     */
    @ApiModelProperty("角色id")
    @NotBlank(message = "角色Id不能为空")
    private Long id;

    /**
     * 角色中文名称 角色中文名称
     */
    @ApiModelProperty("角色中文名")
    private String name;

    /**
     * 角色说明
     */
    @ApiModelProperty("角色说明")
    private String remark;

    /**
     * 权限码 权限编码
     */
    @ApiModelProperty("角色权限")
    private List<String> authData;

}