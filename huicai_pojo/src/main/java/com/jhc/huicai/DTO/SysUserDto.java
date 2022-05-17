package com.jhc.huicai.DTO;

import com.jhc.huicai.DO.BasePage;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zeh
 * @Date: 2021/09/14/0:27
 * @Description:
 */
@Data
public class SysUserDto extends BasePage {

    /**
     * 用户编号
     */
    private String number;

    /**
     * 姓名
     */
    private String name;


}