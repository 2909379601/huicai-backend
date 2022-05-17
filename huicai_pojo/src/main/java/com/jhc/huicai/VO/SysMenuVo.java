package com.jhc.huicai.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/09/09/18:02
 * @Description:
 */
@Data
public class SysMenuVo {
    private Integer sort;
    private String label;
    private String path;
    private String icon;
    private String component;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer type;
    private List<SysMenuVo> children;
}