package com.jhc.huicai.utils;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhc.huicai.DO.BasePage;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
public class PageUtil {

    public static <T> IPage<T> pageSize(List<T> list, BasePage dto) {
        if (dto.getCurrentPage()==0){
            dto.setCurrentPage(1);
        }
        ArrayList<T> result = new ArrayList<>();
        for (int i = (dto.getCurrentPage()-1) * dto.getPageSize(); i < (dto.getCurrentPage()-1)  * dto.getPageSize() + dto.getPageSize(); i++) {
            if (i >= list.size()) {
                break;
            }
            if (ObjectUtil.isNotEmpty(list.get(i))) {
                result.add(list.get(i));
            }
        }

        IPage<T> pageData = new Page<>();
        pageData.setRecords(result);
        pageData.setCurrent(result.size()).setSize(dto.getPageSize()).setTotal(list.size());
        return pageData;
    }
}