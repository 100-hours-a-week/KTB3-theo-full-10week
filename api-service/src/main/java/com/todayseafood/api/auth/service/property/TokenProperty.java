package com.todayseafood.api.auth.service.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.auth.token")
public class TokenProperty {
    private long accessTokenTtlMillis;
    private long refreshTokenTtlMillis;
    private String secretkey;
    private String issuer;
}


