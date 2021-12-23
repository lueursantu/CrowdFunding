package com.santu.crowd;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Santu
 * @date 2021/12/23 11:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void testRedisConn(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("apple", "red");
    }
}
