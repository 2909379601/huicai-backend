package com.jhc.huicai.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jhc.huicai.DO.BaseDo;
import com.jhc.huicai.DO.SysRole;
import com.jhc.huicai.DTO.SysAddUserRoleDto;
import com.jhc.huicai.DTO.SysRoleAddDto;
import com.jhc.huicai.DTO.SysRoleUpdateDto;
import com.jhc.huicai.service.ISysRoleService;
import com.jhc.huicai.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/17
 * @Time: 9:08
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@RestController
@RequestMapping("/sysRole")
@Api(tags = "系统 权限管理接口")
public class SysRoleController {

    @Autowired
    private ISysRoleService roleService;

    @ApiOperation("新增角色")
    @PostMapping("/addRole")
    public CommonResult addRole(@RequestBody SysRoleAddDto sysRoleAddDto) {
        boolean result = roleService.addRole(sysRoleAddDto);
        if (result) {
            return CommonResult.success("新增角色成功");
        } else {
            return CommonResult.failed("新增失败");
        }
    }

    @ApiOperation("增加用户角色")
    @PutMapping("/addUserRole")
    public CommonResult addUserRole(@RequestBody SysAddUserRoleDto addUserRoleDto) {
        boolean b = roleService.addUserRole(addUserRoleDto);
        if (b) {
            return CommonResult.success("给用户添加角色成功");
        } else {
            return CommonResult.failed("给用户添加角色失败");
        }
    }

    @ApiOperation("删除角色(可批量)")
    @DeleteMapping()
    public CommonResult deletesRole( @RequestBody List<Long> ids) {
        boolean result = roleService.removeByIds(ids);
        if (result) {
            return CommonResult.success("删除角色成功");
        } else {
            return CommonResult.failed("删除角色失败");
        }
    }

    @ApiOperation("更新角色")
    @PutMapping("/UpdateRole")
    public CommonResult updateRole(@RequestBody SysRoleUpdateDto sysRoleUpdateDto) {
        boolean result = roleService.updateRole(sysRoleUpdateDto);
        if (result) {
            return CommonResult.success("更新角色成功");
        } else {
            return CommonResult.failed("更新角色失败");
        }
    }

    @ApiOperation("查询所有角色")
    @GetMapping("/all")
    public CommonResult searchRole() {
        return CommonResult.success(roleService.list(new QueryWrapper<SysRole>().lambda().orderByAsc(BaseDo::getCreateTime)));
    }
}