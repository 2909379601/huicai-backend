package com.jhc.huicai.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/09/08/9:46
 * @Description:
 */
public enum GenderType {
    /**
     * 性别类型
     */
    Default(0, "默认"),Male(1, "男") ,FeMale(2, "女"),Secrecy(3, "保密");
    private String type;
    private Integer code;

    GenderType(Integer code, String type) {
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