package com.jhc.huicai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhc.huicai.DO.BasStudent;
import com.jhc.huicai.DTO.BasStudentDto;
import com.jhc.huicai.VO.BasStudentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 学生档案 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2022-01-08
 */
@Mapper
public interface BasStudentMapper extends BaseMapper<BasStudent> {
    IPage<BasStudentVo> getStuPageData(Page page, @Param("dto") BasStudentDto dto);

}