package com.jhc.huicai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jhc.huicai.DO.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * cms_role  Mapper 接口
 * </p>
 *
 * @author zeh
 * @since 2021-07-02
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    SysRole selectPermission(Long number);
}