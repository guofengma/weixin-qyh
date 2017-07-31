package com.bingkun.weixin.qyh.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by pengjikun on 2017/7/31.
 */
public interface RedisService<K, V> {
    /**
     * 存值，无限时间
     * @param key
     * @param value
     */
    void set(K key, V value);

    /**
     * 存值，有过期时间（默认单位S）
     * @param key
     * @param value
     * @param expTime 过期时间
     */
    void set(K key, V value, int expTime);

    /**
     * 存值，有过期时间
     * @param key
     * @param value
     * @param expTime 过期时间
     * @param timeUnit 单位 TimeUnit.SECONDS 秒；TimeUnit.MINUTES 分钟；TimeUnit.HOURS 小时；TimeUnit.DAYS 天
     */
    void set(K key, V value, int expTime, TimeUnit timeUnit);

    /**
     * 取值
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 根据key删除值
     * @param key
     */
    void delete(K key);

}
