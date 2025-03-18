package project.ecm.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final String REFRESH_TOKEN_PREFIX = "refresh:";
    private static final long REFRESH_TOKEN_EXPIRATION = 7; // 7 days

    private final StringRedisTemplate redisTemplate;

    public void saveRefreshToken(String email, String refresh) {
        redisTemplate.opsForValue()
                .set(REFRESH_TOKEN_PREFIX + email, refresh, REFRESH_TOKEN_EXPIRATION, TimeUnit.DAYS);
    }

    public Optional<String> getRefreshToken(String email) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + email));
    }

    public void deleteRefreshToken(String email) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + email);
    }
}
