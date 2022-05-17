package com.jhc.huicai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhc.huicai.DO.SysRole;
import com.jhc.huicai.DTO.SysAddUserRoleDto;
import com.jhc.huicai.DTO.SysRoleAddDto;
import com.jhc.huicai.DTO.SysRoleUpdateDto;

/**
 * <p>
 * cms_role  服务类
 * </p>
 *
 * @author zeh
 * @since 2021-07-02
 */
public interface ISysRoleService extends IService<SysRole> {

    boolean addRole(SysRoleAddDto sysRoleAddDto);

    boolean updateRole(SysRoleUpdateDto sysRoleUpdateDto);

    boolean addUserRole(SysAddUserRoleDto addUserRoleDto);

}