package com.jhc.huicai.utils;

import com.jhc.huicai.enums.*;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
public class EnumGenerator {
    // 生成路径
    static String path ="device_controller\\src\\main\\resources\\enum.js";

    public static void main(String[] args) {
        StringBuffer bufferObject = new StringBuffer();
        long begin = System.currentTimeMillis();
        try {
            bufferObject.append("/**\n" +
                    "* 枚举配置\n" +
                    "*/\r\n\r\n");

            // 需要生成的枚举类
            toJson("权限", PermissionType.class, bufferObject);
            toJson("性别", GenderType.class,bufferObject);
            toJson("预约状态", AppointmentStatusType.class,bufferObject);
            toJson("时间段状态", PeriodStatusType.class,bufferObject);
            toJson("签到方式", SignMethodType.class,bufferObject);
            toJson("公告状态", NoticeStatusType.class,bufferObject);
               StringBuffer buffer = bufferObject.append("\r\n");
            writeJs(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行耗时:" + (end - begin) + " 毫秒");
    }

    private static void toJson(String s,Class clazz, StringBuffer bufferObject) throws Exception {
        addComment(s,bufferObject);
        String key = toLowerCaseFirstOne(clazz.getSimpleName());
        toJson(clazz, key, bufferObject);
    }

    private static void toJson(Class clazz, String key, StringBuffer bufferObject) throws Exception {
        Object[] objects = clazz.getEnumConstants();
        Method name = clazz.getMethod("name");
        Method getDesc = clazz.getMethod("getType");
        Method getCode = clazz.getMethod("getCode");

        // 生成对象
        bufferObject.append("export const ").append(key).append(" = {\r\n");
        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];
            if (getCode == null) {
                bufferObject.append("\"null").append("\":\"").append(getDesc.invoke(obj)).append("\"");
            } else {
                bufferObject.append("\t\"").append(getCode.invoke(obj)).append("\":\"").append(getDesc.invoke(obj)).append("\"");
            }
            if (i <= objects.length - 1) {
                bufferObject.append(",\r\n");
            }
        }
        bufferObject.append("}\r\n\r\n");


    }

    /**
     * 写文件
     * @param stringBuffer
     */
    public static void writeJs(StringBuffer stringBuffer) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            System.out.println(path);
            osw.write(stringBuffer.toString());
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 增加注释
     * @param s
     * @param buffer
     */
    public static void addComment(String s,StringBuffer buffer) {
        buffer.append("// ").append(s).append("\r\n");
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String toLowerCaseFirstOne(String str){
        if(Character.isLowerCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    /**
     * 功能：驼峰转大写下划线，并去掉_ENUM
     * 如：SectionChargeEnum 变成 SECTION_CHARGE
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        String result = underline(str).toString();
        return result.substring(1, result.length()).toUpperCase().replace("_ENUM", "");
    }

    /**
     * 驼峰转下划线，第一位是下划线
     * 如：SectionChargeEnum 变成 _section_charge_enum
     * @param str
     * @return
     */
    private static StringBuffer underline(String str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return underline(sb.toString());
    }
}