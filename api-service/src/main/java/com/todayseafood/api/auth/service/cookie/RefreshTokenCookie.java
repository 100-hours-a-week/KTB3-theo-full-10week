package com.todayseafood.api.auth.service.cookie;

import com.todayseafood.api.auth.service.property.CookieProperty;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenCookie {
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
