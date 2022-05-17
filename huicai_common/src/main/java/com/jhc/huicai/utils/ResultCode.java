package com.jhc.huicai.utils;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
public enum ResultCode implements IErrorCode{

    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败"),
    VALIDATE_FAILED(404,"数据校验失败"),
    UNAUTHORIZED(401,"暂未登陆或token已过期"),
    FORBIDDEN(403,"没有相关权限");


    private int code;
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}