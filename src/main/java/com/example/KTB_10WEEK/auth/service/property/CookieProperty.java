package com.example.KTB_10WEEK.auth.service.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "application.auth.cookie")
public class CookieProperty {
    private final String refreshTokenCookieName;
    private final String refreshTokenCookiePath;
    private final long refreshTokenCookieMaxAge;
    private final String refreshTokenCookieSameSite;

    public CookieProperty(String refreshTokenCookieName, String refreshTokenCookiePath,
                          long refreshTokenCookieMaxAge, String refreshTokenCookieSameSite) {
        this.refreshTokenCookieName = refreshTokenCookieName;
        this.refreshTokenCookiePath = refreshTokenCookiePath;
        this.refreshTokenCookieMaxAge = refreshTokenCookieMaxAge;
        this.refreshTokenCookieSameSite = refreshTokenCookieSameSite;
    }
}
