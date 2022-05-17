package com.jhc.huicai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jhc.huicai.BO.AdminSaveDetails;
import com.jhc.huicai.DTO.SysUpdatePersonalPasswordDto;
import com.jhc.huicai.DTO.SysUserDto;
import com.jhc.huicai.DTO.SysUserLoginDto;
import com.jhc.huicai.DTO.SysUserRegisterDto;
import com.jhc.huicai.VO.RedisUserInfo;
import com.jhc.huicai.VO.SysUserVO;
import com.jhc.huicai.service.ISysUserService;
import com.jhc.huicai.service.RedisService;
import com.jhc.huicai.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:08
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@RestController
@RequestMapping("/sysUser")
@Api(tags="用户接口")
public class UserController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AdminSaveDetails saveDetails;


    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public CommonResult register(@RequestBody @Validated SysUserRegisterDto registerDto) {
        return userService.register(registerDto);
    }


    /**
     * 用户登录
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public CommonResult login(@RequestBody @Validated SysUserLoginDto cmsLogin){
        RedisUserInfo redisUserInfo = userService.login(cmsLogin.getNumber(), cmsLogin.getPassword());
        if (redisUserInfo.getToken() == null) {
            return CommonResult.failed("登录失败，或存在密码不正确");
        }
        return CommonResult.success(redisUserInfo);
    }

    /**
     * 用户登出
     */
    @DeleteMapping("/logout")
    @ApiOperation("用户登出")
    public CommonResult logout() {
        redisService.remove("USER",saveDetails.getSysUser().getNumber());
        return CommonResult.success("登出成功");
    }

    /**
     * 修改用户密码
     */
    @PutMapping("/personalPassword")
    @ApiOperation("修改用户密码")
    public CommonResult updatePersonalPassword(@RequestBody SysUpdatePersonalPasswordDto cmsUpdatePasswordDto) {
        return userService.updatePersonalPassword(cmsUpdatePasswordDto);
    }

    /**
     * 用户密码重置(批量）
     * @param numbers
     * @return
     */
    @PutMapping("/resetPas")
    @ApiOperation("用户密码重置(批量）user:resetPas")
    public CommonResult resetPas(@RequestBody List<String> numbers) {
        Boolean aBoolean = userService.resetPas(numbers);
        return aBoolean
                ? CommonResult.success("重置成功")
                : CommonResult.failed("重置失败，部分数据不存在");
    }

    @GetMapping("/getUserInfoByUserNum")
    @ApiOperation ("根据用户编号获取用户信息")
    public CommonResult getUserInfoByUserNum(String number){
        return  userService.getUserInfoByUserNum( number);

    }


    @GetMapping("/getAllUser")
    @ApiOperation ("获取全部用户")
    public  CommonResult getAllUser(SysUserDto allSysUserDto){
        IPage<SysUserVO> cmsUserPageData = userService.getAllUserPageData(allSysUserDto);
        return CommonResult.success(cmsUserPageData);
    }
}