package com.jhc.huicai.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/10/19/23:26
 * @Description:
 */
@Data
public class SysUserRoleAddDto {
    @NotNull(message = "用户ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty(value = "用户ID")
    private List<Long> idList;

    @NotNull(message = "角色不能为空")
    @ApiModelProperty(value = "角色ID")
    private String roleId;

}