package com.jhc.huicai.DO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 用户编号
     */
    private String number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 0默认1男2女3保密
     */
    private Integer sex;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限集合
     */
    private String authData;


}