package com.jhc.huicai.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jhc.huicai.DO.SysPermission;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/07/01/16:20
 * @Description:
 */
@Data
public class RedisUserInfo {

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 用户number
     */
    private String Number;

    /**
     * 用户name
     */
    private String Name;

    /**
     * 用户nickname
     */
    private String Nickname;

    /**
     * token
     */
    private String token;

    /**
     * 权限列表
     */
    private List<SysPermission> permissionList;

    /**
     * 按钮列表
     */
    private List<String> permissionValueList;

    /**
     * 头像
     */
    private String photo;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 菜单集合
     */
    private List<SysTreePermissionVo> menuList;


}