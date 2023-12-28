package com.example.code.repositories;

public interface JwtTokenRepository {
    void save(String key, String value, Long expirationSeconds);

    void deleteAllByUserId(Long userId);

    boolean isTokenValid(String tokenId);
}
