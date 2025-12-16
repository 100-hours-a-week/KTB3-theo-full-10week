package com.todayseafood.api.auth.service.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.auth.cookie")
public class CookieProperty {
    private String refreshTokenCookieName;
    private String refreshTokenCookiePath;
    private long refreshTokenCookieMaxAge;
    private String refreshTokenCookieSameSite;
    private String refreshTokenCookieDomain;
}
