package com.jhc.huicai.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/09/08/9:46
 * @Description:
 */
public enum AppointmentStatusType {
    /**
     * 预约状态类型
     */
    Audit(1, "待审核"), AuditFailed(2, "审核不通过"), Approved(3,"审核通过")
    ,SignedIn(4,"已签到"),SignedOut(5,"已签退"),CancelOUT(6,"取消预约");
    private String type;
    private Integer code;

    AppointmentStatusType(Integer code, String type) {
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