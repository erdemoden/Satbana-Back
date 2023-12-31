package com.woh.mailservice.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RedisStore {
    private final RedisTemplate template;

    public void put(final Object key, final Object value){
        template.opsForValue().set(key,value,300, TimeUnit.SECONDS);
    }
    public <T> T getValue(final Object key){
        return (T) template.opsForValue().get(key);
    }
}
