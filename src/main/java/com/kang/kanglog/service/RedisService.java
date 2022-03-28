package com.kang.kanglog.service;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

//@RequiredArgsConstructor
//@Service //요거는 자동 빈 주입이 아닌 수동 빈 주입으로..
public class RedisService { //refreshToken을 저장하기 위한 용도

    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    public  String getData(String key) {
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value) {
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    public void setDataExpire(String key, String value, int duration) {

        System.out.println("key: " + key);

        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Duration tempDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value,tempDuration);

        System.out.println("check: "+valueOperations.get(key));
    }

    public void deleteData(String key){
        stringRedisTemplate.delete(key);
    }

}
