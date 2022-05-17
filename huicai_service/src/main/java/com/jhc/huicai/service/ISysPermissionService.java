package com.jhc.huicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhc.huicai.DO.SysPermission;
import com.jhc.huicai.DTO.SysPermissionAdd;
import com.jhc.huicai.DTO.SysUserPermissionAdd;
import com.jhc.huicai.VO.SysMenuVo;
import com.jhc.huicai.utils.CommonResult;

import java.util.List;

/**
 * <p>
 * cms_permission  服务类
 * </p>
 *
 * @author zhengyue
 * @since 2020-05-18
 */
public interface ISysPermissionService extends IService<SysPermission> {
    /**
     * 获取用户按钮
     *
     * @param number 用户账号  type (0返回权限，1返回权限个数）
     * @return 用户权限列表
     */
    List<String> getPermission(String number,String type);

    /**
     * 获取用户树形菜单权限
     *
     * @param number 用户账号 type类型
     * @return 用户权限列表
     */
    List<SysMenuVo> sysTreePermissions(String number);


    /**
     * 添加用户权限
     * @param permissionAdd
     * @return
     */
    boolean userAddPermission(SysUserPermissionAdd permissionAdd);


    /**
     * 获取全部树形菜单权限
     *
     * @return 权限列表
     */

    CommonResult treePermission();

    Boolean  deleteByIds(List<Long> permissionId);

    CommonResult getByRolePermission(Long number);

    CommonResult addPermission(SysPermissionAdd permissionAdd);


}