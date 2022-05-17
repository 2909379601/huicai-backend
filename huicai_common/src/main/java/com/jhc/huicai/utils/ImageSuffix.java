package com.jhc.huicai.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
@Component
public class ImageSuffix {
    @Value("${web.images-suffix}")
    List<String> suffix = new ArrayList<>();

    public boolean isImages(String fileName){
        System.out.println(suffix);
        //获取后缀名
        String str = FileNameUtil.getSuffix(fileName).toUpperCase();
        //校验后缀名
        if (suffix.contains(str)){
            return true;
        }
        return false;
    }
}