package com.jhc.huicai.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.jhc.huicai.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 **/
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存储数据到指定区域
     *
     * @param region 区域
     * @param key    键
     * @param value  值
     */
    @Override
    public void set(String region, String key, String value) {
        stringRedisTemplate.opsForValue().set(region + ":" + key, value);
    }

    /**
     * 存储数据并设置时间
     *
     * @param region 区域
     * @param key    键
     * @param value  值
     * @param expire 过期时间
     */
    @Override
    public void set(String region, String key, String value, long expire) {
        String rKey = region + ":" + key;
        stringRedisTemplate.opsForValue().set(rKey, value);
        stringRedisTemplate.expire(rKey, expire, TimeUnit.SECONDS);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> List<T> get(String region, Class<T> clazz) {
        ArrayList<T> list = new ArrayList();
        Set<String> keys = stringRedisTemplate.keys(region + ":*");
        for (String key : keys) {
            String s = stringRedisTemplate.opsForValue().get(key);
            T t = JSONObject.parseObject(s, clazz);
            list.add(t);
        }
        return list;
    }

    /**
     * @Description: 通过区域和键获取数据
     * @Author: zeh
     * @Date:
     * @Param: [region, key]
     * @Return: java.lang.String
     */
    @Override
    public String get(String region, String key) {
        return stringRedisTemplate.opsForValue().get(region + ":" + key);
    }

    /**
     * 设置超期时间
     *
     * @param key    键
     * @param expire 过期时间
     * @return 是否成功
     */
    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 设置区域超期时间
     *
     * @param key    键
     * @param expire 过期时间
     * @return 是否成功
     */
    @Override
    public boolean expire(String region, String key, long expire) {
        return stringRedisTemplate.expire(region + ":" + key, expire, TimeUnit.SECONDS);
    }

    /**
     * 删除数据
     *
     * @param key 键
     */
    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 删除区域数据
     *
     * @param key 键
     * @Param region 区域
     */
    @Override
    public void remove(String region, String key) {
        stringRedisTemplate.delete(region + ":" + key);
    }

    /**
     * @Description: 删除指定区域的数据
     * @Author: zeh
     * @Date:
     * @Param: region
     * @Return:
     */
    @Override
    public void removeRegion(String region) {
        Set<String> keys = stringRedisTemplate.keys(region + ":*");
        stringRedisTemplate.delete(keys);
    }

    /**
     * 自增操作
     *
     * @param key   键
     * @param delta 自增步长
     * @return
     */
    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }
}