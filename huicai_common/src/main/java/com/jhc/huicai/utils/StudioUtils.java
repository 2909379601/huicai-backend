package com.jhc.huicai.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: Erruihhh
 * @Date: 2022/5/16
 * @Time: 15:10
 * @PROJECT_NAME: huicai-backend
 * @Description:
 */
public class StudioUtils {
    /**
     * 去头去尾
     *
     * @param content   字符串
     * @param startChar 开头字符串
     * @param endChar   结束字符串
     * @return 去掉后的字符串
     */
    public static String removeStartEnd(String content, String startChar, String endChar) {
        if (content == null) {
            return null;
        }
        if (startChar == null) {
            startChar = "";
        }
        if (endChar == null) {
            endChar = "";
        }
        return StrUtil.removePrefix(StrUtil.removeSuffix(content, endChar), startChar);
    }


    public static String getChinese(String content, int count) {
        Pattern p = null;
        Matcher m = null;
        String value = null;
        p = Pattern.compile("([\u4e00-\u9fa5]+)");
        m = p.matcher(content);
        String result = "";
        while (m.find()) {
            value = m.group(0);
            result = result + value;
            if (count > 0) {
                count--;
            } else {
                return result;
            }
        }
        return result;
    }

    /**
     * 根据当前日期和操作天数生成操作后的日期
     *
     * @param currentDate 当前日期
     * @param optionDays  操作天数
     * @return 操作后的日期
     */
    public static Date getDate(Date currentDate, Integer optionDays) {
        Calendar calendar = Calendar.getInstance();
        //设置calender时间
        calendar.setTime(currentDate);
        //添加时间
        calendar.add(Calendar.DATE, optionDays);
        return calendar.getTime();
    }

    /**
     * 获取IP地址
     *
     * @param request 请求服务
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    /**
     * 获取Mac地址
     *
     * @param request 请求服务
     * @return Mac地址
     */
    public static String getMacByIp(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }

            //根据ip获取mac
            String macAddress = "";
            String line;
// 如果为127.0.0.1,则获取本地MAC地址
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress) || "localhost".equals(ipAddress) ) {
                //注意getLocalIp().equals(ip)，由于else中的方法获取不到本机ip对应的的mac，所以放到这里，调用的getLocalIp()
                InetAddress inetAddress = InetAddress.getLocalHost();
                byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
                        .getHardwareAddress();
// 下面代码是把mac地址拼装成String
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    if (i != 0) {
                        sb.append("-");
                    }
// mac[i] & 0xFF 是为了把byte转化为正整数
                    String s = Integer.toHexString(mac[i] & 0xFF);
                    sb.append(s.length() == 1 ? 0 + s : s);
                }
// 把字符串所有小写字母改为大写成为正规的mac地址并返回
                macAddress = sb.toString().trim().toUpperCase();
                return macAddress;
            } else { // 获取非本地IP的MAC地址


                try {
                    System.out.println(ipAddress);
                    Process p = Runtime.getRuntime()
                            .exec("nbtstat -A " + ipAddress);
                    System.out.println("===process==" + p);
                    InputStreamReader ir = new InputStreamReader(p.getInputStream());

                    BufferedReader br = new BufferedReader(ir);

                    while ((line = br.readLine()) != null) {
                        if (line.indexOf("MAC") > 1) {
                            macAddress = line.substring(line.indexOf("MAC") + 9,
                                    line.length());
                            macAddress = macAddress.trim();
                            System.out.println("macAddress:" + macAddress);
                            break;
                        }
                    }
                    p.destroy();
                    br.close();
                    ir.close();
                } catch (IOException ex) {
                }


// //由于我测试的时候，使用"nbtstat -A " + ip获取不到一些Mac，
// //所以换成"arp -A " + ip
//                final String MAC_ADDRESS_PREFIX = "MAC Address = ";
//                try {
//                    Process p = Runtime.getRuntime().exec("nbtstat -A " + ipAddress);
//                    InputStreamReader isr = new InputStreamReader(p.getInputStream());
//                    BufferedReader br = new BufferedReader(isr);
//                    while ((line = br.readLine()) != null) {
//                        if (line != null) {
//                            int index = line.indexOf(MAC_ADDRESS_PREFIX);
//                            if (index != -1) {
//                                macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();
//                            }
//                        }
//                    }
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace(System.out);
//                }
//                try {
//                    Process p = Runtime.getRuntime().exec("arp -A " + ipAddress);
//                    InputStreamReader isr = new InputStreamReader(p.getInputStream());
//                    BufferedReader br = new BufferedReader(isr);
//                    while ((line = br.readLine()) != null) {
//                        if (line != null) {
//                            int index = line.indexOf(ipAddress);
//                            if (index != -1) {
//                                macAddress = line.substring(index + ipAddress.length() + 10, index + ipAddress.length() + 27).trim().toUpperCase();
//
//                            }
//
//                        }
//
//                    }
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace(System.out);
//                }
                return ipAddress+macAddress;
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }


    /**
     * 日期去掉时间
     *
     * @param date
     * @return
     */
    public static Date removeTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date afterDate = null;
        try {
            afterDate = format.parse(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return afterDate;
    }

    /**
     * 日期转字符
     *
     * @param date
     * @return
     */
    public static String toDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 日期转时分秒字符串
     *
     * @param date
     * @return
     */
    public static String toDateTimeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(date);
    }

    /**
     * name与number连接
     *
     * @param name
     * @param number
     * @return
     */
    public static String addNameAndNumber(String name, String number) {
        return name + "@" + number;
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static List<String> toStrList(List<Long> longList) {
        List<String> strList = longList.stream().map(item -> String.valueOf(item)).collect(Collectors.toList());
        return strList;
    }

    public static List<Long> toLongList(List<String> stringList) {
        List<Long> longList = stringList.stream().map(item -> Long.valueOf(item)).collect(Collectors.toList());
        return longList;
    }

    /**
     * list分页
     */
    public static IPage listPage(List list, Integer current, Integer size) {
        IPage page = new Page<>(current, size);

        page.setTotal(list.size());
//                分页
        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数

        if (count % size == 0) {
            pageCount = count / size;
        } else {
            pageCount = count / size + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (current != pageCount) {
            fromIndex = (current - 1) * size;
            toIndex = fromIndex + size;
        } else {
            fromIndex = (current - 1) * size;
            toIndex = count;
        }
        page.setRecords(list.subList(fromIndex, toIndex));

        return page;
    }
}