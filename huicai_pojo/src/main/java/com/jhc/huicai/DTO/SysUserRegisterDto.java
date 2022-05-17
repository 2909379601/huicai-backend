package com.jhc.huicai.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/07/02/10:36
 * @Description:
 */
@Data
@ApiModel("CmsUserRegisterDto :: 用户注册")
public class SysUserRegisterDto {
    /**
     * 号
     */
    @ApiModelProperty("帐号")
    @NotBlank(message = "帐号不能为空")
    private String number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;


    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Integer sex;

    /**
     * 是否学生true是学生false是老师
     */
    @ApiModelProperty("类型")
    private Boolean isStudent;

//        /**
//         * 邮箱
//         */
//        @ApiModelProperty("邮箱")
//        @NotBlank(message = "邮箱不能为空")
//        private  String email;
//
//        /**
//         * 验证码
//         */
//        @ApiModelProperty("验证码")
//        @NotBlank(message = "验证码不能为空")
//        private String Pin;


}