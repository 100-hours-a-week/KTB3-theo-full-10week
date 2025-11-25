package com.example.KTB_10WEEK.auth.service.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "application.auth.token")
public class TokenProperty { // setter랑 생성자 꼭 필요
    private final long accessTokenTtlMillis;
    private final long refreshTokenTtlMillis;
    private final String secretkey;
    private final String issuer;

    public TokenProperty(long accessTokenTtlMillis, long refreshTokenTtlMillis,
                         String secretkey, String issuer) {
        this.accessTokenTtlMillis = accessTokenTtlMillis;
        this.refreshTokenTtlMillis = refreshTokenTtlMillis;
        this.secretkey = secretkey;
        this.issuer = issuer;
    }
}
