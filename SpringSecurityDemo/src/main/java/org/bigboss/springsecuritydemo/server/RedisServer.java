package org.bigboss.springsecuritydemo.server;

import org.bigboss.springsecuritydemo.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: maifuwa
 * @date: 2024/3/18 下午4:51
 * @description:
 */
@Service
public class RedisServer {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${redis.max-exp:360000}")
    private long maxExp;

    @Value("${redis.min-exp:180000}")
    private long minExp;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, this.randomAddMillisecond(0));
    }

    public void setWithExp(String key, String value, long exp, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, randomAddMillisecond(timeUnit.toMillis(exp)), TimeUnit.MILLISECONDS);
    }

    private Long randomAddMillisecond(long exp) {
        return exp + RandomUtils.randomLong(minExp, maxExp);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }


}
