package com.jhc.huicai.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/09/08/9:46
 * @Description:
 */
public enum PeriodStatusType {
    /**
     * 时间段状态类型
     */
    Start(0, "启动"), Deactivate(1, "停用");
    private String type;
    private Integer code;

    PeriodStatusType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}