package com.example.KTB_10WEEK.auth.service.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.auth.cookie")
public record CookieProperty(
        String refreshTokenCookieName,
        String refreshTokenCookiePath,
        long refreshTokenCookieMaxAge,
        String refreshTokenCookieSameSite
) {
}
