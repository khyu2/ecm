package project.ecm.global.auth.controller;

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

    @PostMapping("/refresh")
    public ResponseEntity<?> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtService.extractRefreshToken(request)
                .orElseThrow(() -> new BaseException(BaseExceptionType.INVALID_REFRESH_TOKEN));

        if (jwtService.isTokenValid(refreshToken)) {
            String username = jwtService.extractUsername(refreshToken)
                    .orElseThrow(() -> new BaseException(BaseExceptionType.INVALID_REFRESH_TOKEN));

            refreshTokenService.getRefreshToken(username)
                    .ifPresentOrElse(refresh -> {
                        String accessToken = jwtService.createAccessToken(username);
                        response.setHeader(JwtService.accessHeader, JwtService.BEARER + accessToken);
                    }, () -> {
                        throw new BaseException(BaseExceptionType.INVALID_REFRESH_TOKEN);
                    });
        }

        throw new BaseException(BaseExceptionType.INVALID_REFRESH_TOKEN);
    }
}
