package com.bingkun.weixin.qyh.service.impl;

import com.bingkun.weixin.qyh.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by pengjikun on 2017/7/31.
 */
@Component
public class RedisServiceImpl<K, V> implements RedisService<K, V>{
    @Autowired
    RedisTemplate<K, V> redisTemplate;

    @Override
    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(K key, V value, int expTime) {
        redisTemplate.opsForValue().set(key, value, expTime, TimeUnit.SECONDS);
    }

    @Override
    public void set(K key, V value, int expTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expTime, timeUnit);
    }

    @Override
    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(K key) {
        redisTemplate.delete(key);
    }
}
