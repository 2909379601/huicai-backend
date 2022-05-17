package com.jhc.huicai.DO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * cms_role
 * </p>
 *
 * @author zeh
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole extends BaseDo {

    private static final long serialVersionUID=1L;



    /**
     * 角色中文名称 角色中文名称
     */
    private String name;

    /**
     * 权限码 权限编码
     */
    private String authData;

    /**
     * 角色说明
     */
    private String remark;




}