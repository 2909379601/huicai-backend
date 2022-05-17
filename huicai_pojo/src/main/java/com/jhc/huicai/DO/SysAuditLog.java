package com.jhc.huicai.DO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 访问日志信息
 * </p>
 *
 * @author zeh
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysAuditLog extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 业务名称
     */
    private String title;

    /**
     * 日志类型 10:操作日志 20:错误日志
     */
    private Integer type;

    /**
     * 请求IP
     */
    private String address;

    /**
     * 请求资源路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String reqMethod;

    /**
     * 请求参数
     */
    private String reqParam;

    /**
     * 返回参数
     */
    private String resParam;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 耗时
     */
    private String timeout;

    /**
     * 用户唯一编号
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String userName;


}