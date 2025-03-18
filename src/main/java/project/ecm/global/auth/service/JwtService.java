package project.ecm.global.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private String accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private String refreshExpiration;

    public static final String accessHeader = "Authorization";
    public static final String refreshHeader = "Refresh-Authorization";
    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    public static final String USERNAME_CLAIM = "email";
    public static final String BEARER = "Bearer ";

    public String createAccessToken(String email) {
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withClaim(USERNAME_CLAIM, email)
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(accessExpiration)))
                .sign(Algorithm.HMAC512(secret));
    }

    public String createRefreshToken(String email) {
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withClaim(USERNAME_CLAIM, email)
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(refreshExpiration)))
                .sign(Algorithm.HMAC512(secret));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(access -> access.startsWith(BEARER))
                .map(access -> access.substring(BEARER.length()));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refresh -> refresh.startsWith(BEARER))
                .map(refresh -> refresh.substring(BEARER.length()));
    }

    public Optional<String> extractUsername(String token) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token)
                    .getClaim(USERNAME_CLAIM)
                    .asString());
        } catch (Exception e) {
            log.error("Failed to extract email from access token", e);
            return Optional.empty();
        }
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            log.error("Failed to validate access token", e);
            return false;
        }
    }
}
