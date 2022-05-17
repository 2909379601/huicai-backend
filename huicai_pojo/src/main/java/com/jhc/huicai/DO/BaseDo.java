package com.jhc.huicai.DO;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@Data
@Accessors(chain = true)
public class BaseDo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;


    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建用户
     */
    private String createUser;


    /**
     * 更新用户
     */
    private String updateUser;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean isDel;
}