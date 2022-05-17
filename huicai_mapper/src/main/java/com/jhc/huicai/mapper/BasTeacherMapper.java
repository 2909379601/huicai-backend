package com.jhc.huicai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhc.huicai.DO.BasTeacher;
import com.jhc.huicai.DTO.BasTeacherDto;
import com.jhc.huicai.VO.BasTeacherVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 教师档案 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2022-01-08
 */
@Mapper
public interface BasTeacherMapper extends BaseMapper<BasTeacher> {
    IPage<BasTeacherVo> getTeachPageData(Page page, @Param("dto") BasTeacherDto pageVo);

}