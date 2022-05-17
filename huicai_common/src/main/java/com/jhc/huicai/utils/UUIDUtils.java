package com.jhc.huicai.utils;

import java.util.UUID;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}