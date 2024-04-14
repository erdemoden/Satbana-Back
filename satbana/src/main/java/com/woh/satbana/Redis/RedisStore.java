package com.woh.satbana.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RedisStore {
    private final RedisTemplate template;

    public void put(final Object key, final Object value, final long time){
        template.opsForValue().set(key,value,time, TimeUnit.SECONDS);
    }
    public <T> T getValue(final Object key){
        return (T) template.opsForValue().get(key);
    }
    public void delete(final Object key){
        template.delete(key);
    }
    public boolean isExists(final Object key){
        return template.hasKey(key);
    }
}
