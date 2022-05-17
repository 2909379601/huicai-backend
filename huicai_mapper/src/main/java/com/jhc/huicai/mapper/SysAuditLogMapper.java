package com.jhc.huicai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jhc.huicai.DO.SysAuditLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 访问日志信息  Mapper 接口
 * </p>
 *
 * @author zeh
 * @since 2021-07-02
 */
@Mapper
public interface SysAuditLogMapper extends BaseMapper<SysAuditLog> {

}