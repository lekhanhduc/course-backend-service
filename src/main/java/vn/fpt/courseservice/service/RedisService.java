package vn.fpt.courseservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void add(String key, String value, int expirationTime) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(expirationTime));
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
