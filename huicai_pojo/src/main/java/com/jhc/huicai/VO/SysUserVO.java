package com.jhc.huicai.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/09/14/0:35
 * @Description:
 */
@Data
public class SysUserVO implements Serializable {
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
     * 权限集合
     */
    private String authData;

    /**
     * 性别 0男1女
     * */
    private Integer sex;
}