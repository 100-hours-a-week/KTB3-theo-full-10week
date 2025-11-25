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
                .from(cookieProperty.getRefreshTokenCookieName(), refreshToken)
                .path(cookieProperty.getRefreshTokenCookiePath())
                .httpOnly(true)
                .secure(true)
                .sameSite(cookieProperty.getRefreshTokenCookieSameSite())
                .maxAge(cookieProperty.getRefreshTokenCookieMaxAge())
                .build();
    }

    // 리프레시 토큰 쿠키 제거용
    public ResponseCookie expire() {
        return ResponseCookie
                .from(cookieProperty.getRefreshTokenCookieName(), "")
                .path(cookieProperty.getRefreshTokenCookiePath())
                .httpOnly(true)
                .secure(true)
                .sameSite(cookieProperty.getRefreshTokenCookieSameSite())
                .maxAge(0) // 만료 시간
                .build();
    }
}
