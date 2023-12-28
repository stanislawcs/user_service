package com.example.code.repositories;

import com.example.code.dto.TokenType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class JwtTokenRepositoryImpl implements JwtTokenRepository {

    private ValueOperations<String, String> valueOperations;
    private final RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    private void init() {
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void save(String key, String value, Long expirationSeconds) {
        valueOperations.set(key, value, expirationSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        String key = userId.toString() + ":" + TokenType.ACCESS.toString().toLowerCase() + ":*";
        Set<String> keys = redisTemplate.keys(key);

        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }

    }

    @Override
    public boolean isTokenValid(String tokenId) {
        Set<String> tokens = redisTemplate.keys("*:%s".formatted(tokenId));
        return tokens != null && !tokens.isEmpty();
    }

}