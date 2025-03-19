package project.ecm.global.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ecm.global.auth.service.JwtService;
import project.ecm.global.auth.service.RefreshTokenService;
import project.ecm.global.exception.BaseException;
import project.ecm.global.exception.BaseExceptionType;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Access Token 재발급", description = "Refresh Token 또한 재발급되어 저장됨")
    @PostMapping("/refresh")
    public ResponseEntity<?> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtService.extractRefreshToken(request)
                .orElseThrow(() -> new BaseException(BaseExceptionType.INVALID_REFRESH_TOKEN));

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new BaseException(BaseExceptionType.INVALID_REFRESH_TOKEN);
        }

        String username = jwtService.extractUsername(refreshToken)
                .orElseThrow(() -> new BaseException(BaseExceptionType.INVALID_REFRESH_TOKEN));

        refreshTokenService.getRefreshToken(username)
                .ifPresent(refresh -> {
                    String accessToken = jwtService.createAccessToken(username);
                    response.setHeader(JwtService.accessHeader, JwtService.BEARER + accessToken);
                });

        refreshTokenService.saveRefreshToken(username, jwtService.createRefreshToken(username));

        return ResponseEntity.ok().build();
    }
}
