package com.jhc.huicai.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: jiangzhuohang
 * @Date: 2022/01/15/22:46
 * @Description:
 */
public enum NoticeStatusType {
    /**
     * 公告状态类型
     */
    Draft(0, "草稿"), Publish(1, "发布");
    private String type;
    private Integer code;

    NoticeStatusType(Integer code, String type) {
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