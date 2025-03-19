package project.ecm.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private static final String EMAIL_VERIFICATION_PREFIX = "email:verification:";
    private static final long EMAIL_VERIFICATION_EXPIRATION = 5; // 5분

    private final StringRedisTemplate redisTemplate;

    // 인증 코드 저장
    public void saveVerificationCode(String email, String code) {
        redisTemplate.opsForValue()
                .set(EMAIL_VERIFICATION_PREFIX + email, code, EMAIL_VERIFICATION_EXPIRATION, TimeUnit.MINUTES);
    }

    // 인증 코드 조회
    public Optional<String> getVerificationCode(String email) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(EMAIL_VERIFICATION_PREFIX + email));
    }

    // 인증 코드 검증
    public boolean verifyCode(String email, String inputCode) {
        String storedCode = redisTemplate.opsForValue().get(EMAIL_VERIFICATION_PREFIX + email);
        return storedCode != null && storedCode.equals(inputCode);
    }

    // 인증 코드 삭제
    public void deleteVerificationCode(String email) {
        redisTemplate.delete(EMAIL_VERIFICATION_PREFIX + email);
    }
}