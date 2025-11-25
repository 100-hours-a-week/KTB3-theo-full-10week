package com.example.KTB_10WEEK.auth.service.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.auth.token")
public record TokenProperty(
        long accessTokenTtlMillis,
        long refreshTokenTtlMillis,
        String secretkey,
        String issuer
) {
}

