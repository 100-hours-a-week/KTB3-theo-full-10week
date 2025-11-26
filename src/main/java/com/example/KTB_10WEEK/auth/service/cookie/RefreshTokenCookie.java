package com.example.KTB_10WEEK.auth.service.cookie;

import com.example.KTB_10WEEK.auth.service.property.CookieProperty;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public final class RefreshTokenCookie {
    private final CookieProperty cookieProperty;

    public RefreshTokenCookie(CookieProperty cookieProperty) {
        this.cookieProperty = cookieProperty;
    }

    public ResponseCookie create(String refreshToken) {
        return ResponseCookie
                .from(cookieProperty.refreshTokenCookieName(), refreshToken)
                .path(cookieProperty.refreshTokenCookiePath())
                .httpOnly(true)
//                .secure(true)
                .sameSite(cookieProperty.refreshTokenCookieSameSite())
                .domain(cookieProperty.refreshTokenCookieDomain())
                .maxAge(cookieProperty.refreshTokenCookieMaxAge())
                .build();
    }

    // 리프레시 토큰 쿠키 제거용
    public ResponseCookie expire() {
        return ResponseCookie
                .from(cookieProperty.refreshTokenCookieName(), "")
                .path(cookieProperty.refreshTokenCookiePath())
                .httpOnly(true)
//                .secure(true)
                .sameSite(cookieProperty.refreshTokenCookieSameSite())
                .domain(cookieProperty.refreshTokenCookieDomain())
                .maxAge(0) // 만료 시간
                .build();
    }
}
