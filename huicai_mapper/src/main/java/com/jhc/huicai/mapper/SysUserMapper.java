package com.jhc.huicai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhc.huicai.DO.SysUser;
import com.jhc.huicai.DTO.SearchUserByRoleDto;
import com.jhc.huicai.DTO.SysUserDto;
import com.jhc.huicai.VO.SysUserSearchVO;
import com.jhc.huicai.VO.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2022-01-08
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUserVO> getAllUserPageData(Page page, @Param("dto") SysUserDto allSysUserDto);

    List<SysUserSearchVO> searchUserByRoleId(@Param("dto") SearchUserByRoleDto searchDo);

}