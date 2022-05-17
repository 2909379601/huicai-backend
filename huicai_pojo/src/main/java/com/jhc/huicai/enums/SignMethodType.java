package com.jhc.huicai.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/09/08/9:46
 * @Description:
 */
public enum SignMethodType {
    /**
     * 签到方式类型
     */
    Location(10, "定位"),ScanCode(20, "扫码") ,LocationScanCode(30, "定位和扫码");
    private String type;
    private Integer code;

    SignMethodType(Integer code, String type) {
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