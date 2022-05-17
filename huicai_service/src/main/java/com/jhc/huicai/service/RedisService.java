package com.jhc.huicai.service;


import java.util.List;

/**
 * redis操作Service,
 * 对象和数组都以json形式进行存储
 *
 * @author zeh
 * @date
 */
public interface RedisService {

    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * @Description: 存储指定区域
     * @Author: zeh
     * @Date:
     * @Param: [region, key, value]
     * @Return: void
     */
    void set(String region, String key, String value);

    /**
     * @Description: 存储指定区域
     * @Author: zeh
     * @Date:
     * @Param: [region, key, value]
     * @Return: void
     */
    void set(String region, String key, String value, long expire);

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    String get(String key);

    /**
     * 获取指定区域的所有数据
     *
     * @param region 键
     * @return 值
     */
    <T> List<T> get(String region, Class<T> clazz);

    /**
     * @Description: 通过区域和键获取数据
     * @Author: zeh
     * @Date:
     * @Param: [region, key]
     * @Return: java.lang.String
     */
    String get(String region, String key);

    /**
     * 设置超期时间
     *
     * @param key    键
     * @param expire 过期时间
     * @return 是否成功
     */
    boolean expire(String key, long expire);

    /**
     * 设置区域超期时间
     *
     * @param key    键
     * @param expire 过期时间
     * @return 是否成功
     */
    boolean expire(String region, String key, long expire);

    /**
     * 删除数据
     *
     * @param key 键
     */
    void remove(String key);

    /**
     * 删除区域数据
     *
     * @param key 键
     */
    void remove(String region, String key);

    /**
     * @Description: 删除指定区域的数据
     * @Author: zeh
     * @Date:
     * @Param: region
     * @Return:
     */
    void removeRegion(String region);

    /**
     * 自增操作
     * @param key 键
     * @param delta 自增步长
     * @return
     */
    Long increment(String key, long delta);

}