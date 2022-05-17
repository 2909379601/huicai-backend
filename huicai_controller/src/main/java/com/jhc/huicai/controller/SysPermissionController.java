package com.jhc.huicai.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhc.huicai.DO.SysPermission;
import com.jhc.huicai.DTO.SysPermissionAdd;
import com.jhc.huicai.DTO.SysPermissionBanRestart;
import com.jhc.huicai.DTO.SysPermissionUpdate;
import com.jhc.huicai.DTO.SysUserPermissionAdd;
import com.jhc.huicai.service.ISysPermissionService;
import com.jhc.huicai.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/17
 * @Time: 9:04
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@RestController
@RequestMapping("/sysPermission")
@Api(tags="系统 权限管理接口")
public class SysPermissionController {

    @Autowired
    private ISysPermissionService permissionService;


    /**
     * 更新或添加用户权限
     * @param permissionAdd
     * @return
     */
    @ApiOperation("更新用户权限用户端user:permission:update")
    @PutMapping("/add/permission")
    public CommonResult addPermission(@Validated @RequestBody SysUserPermissionAdd permissionAdd) {
        // 去重
        permissionAdd.setAuthList(permissionAdd.getAuthList().stream().distinct().collect(Collectors.toList()));
        if (permissionService.userAddPermission(permissionAdd)) {
            return CommonResult.success("添加成功");
        }
        return CommonResult.failed("添加失败");
    }

    /**
     * 查询用户的权限
     * @param pageSize
     * @param pageNum
     * @param name
     * @return
     */
    @ApiOperation("查询权限permission:list")
    @GetMapping("/list")
    //  @PreAuthorize("hasAuthority('permission:list')")
    public CommonResult list(
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "name", defaultValue = "") String name
    ) {
        IPage<SysPermission> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(SysPermission::getName, name).orderByDesc(SysPermission::getCreateTime);
        System.out.println(permissionService.page(page, queryWrapper).getRecords().toString());
        return CommonResult.success(permissionService.page(page, queryWrapper));
    }


    /**
     * 查询用户菜单(树形结构）
     * @return
     */
    @ApiOperation("查询菜单(树形结构）permission:tree")
    @GetMapping("/menu/{number}")
//    @PreAuthorize("hasAuthority('permission:tree')")
    public CommonResult menu(@PathVariable String  number) {
        return CommonResult.success( "查询成功",permissionService.sysTreePermissions(number));
    }

    @ApiOperation("查询角色对应权限")
    @GetMapping("/{number}")
    public CommonResult searchRole(@PathVariable Long number) {
        return permissionService.getByRolePermission(number);
    }


    /**
     * 查询全部权限菜单(树形结构）
     * @return
     */
    @ApiOperation("查询全部权限菜单(树形结构）permission:tree")
    @GetMapping()
    public CommonResult tree() {
        return  permissionService.treePermission();
    }


    /**
     * 删除权限
     * @param permissionDelete
     * @return
     */
    @ApiOperation("删除权限permission:delete")
    @DeleteMapping()
    // @PreAuthorize("hasAnyAuthority('permission:delete')")
    public CommonResult delete(@RequestBody @Validated SysPermissionBanRestart permissionDelete){
        if (permissionService.removeByIds(permissionDelete.getPermissionId())) {
            return CommonResult.success("删除成功");
        } else {
            return CommonResult.failed("权限不存在");
        }
    }

    /**
     * 查询所有权限不分页
     * @return
     */
    @ApiOperation("查询所有权限,平面结构permission:all")
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('permission:all')")
    public CommonResult all() {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(SysPermission::getCreateTime);
        return CommonResult.success(permissionService.list(queryWrapper));
    }

    /**
     * 更新用户权限
     * @param permissionUpdate
     * @return
     */
    @ApiOperation("更新权限，permission:update")
    @PutMapping()
    // @PreAuthorize("hasAnyAuthority('permission:update')")
    public CommonResult update(@RequestBody @Validated SysPermissionUpdate permissionUpdate){
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(permissionUpdate, sysPermission);
        if (permissionService.updateById(sysPermission)) {
            return CommonResult.success("更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    /**
     * 添加权限
     * @param permissionAdd
     * @return
     */
    @ApiOperation("添加权限permission:add")
    @PostMapping()
    // @PreAuthorize("hasAnyAuthority('permission:add')")
    public CommonResult addPermission(@RequestBody @Validated SysPermissionAdd permissionAdd) {
        return permissionService.addPermission(permissionAdd);
    }
}