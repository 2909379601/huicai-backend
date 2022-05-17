package com.jhc.huicai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhc.huicai.DO.SysUser;
import com.jhc.huicai.DTO.*;
import com.jhc.huicai.VO.RedisUserInfo;
import com.jhc.huicai.VO.SysUserVO;
import com.jhc.huicai.utils.CommonResult;

import java.util.List;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
public interface ISysUserService extends IService<SysUser> {
    RedisUserInfo login(String number, String password);

    CommonResult register(SysUserRegisterDto registerDto);

    CommonResult updatePersonalPassword(SysUpdatePersonalPasswordDto cmsUpdatePasswordDto);

    Boolean resetPas(List<String> numbers);

    IPage<SysUserVO> getAllUserPageData(SysUserDto allSysUserDto);

    CommonResult getUserInfoByUserNum(String number);

    CommonResult searchUserByRoleId(SearchUserByRoleDto searchDo);

    CommonResult userAddRole(SysUserRoleAddDto cmsUserRoleAdd);

    CommonResult registerAll();
}