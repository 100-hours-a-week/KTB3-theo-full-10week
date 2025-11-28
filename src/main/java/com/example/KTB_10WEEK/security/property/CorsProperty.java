package com.example.KTB_10WEEK.security.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.auth.cors")
public record CorsProperty(
        String allowedOrigins,
        String allowedMethods,
        String allowedHeaders,
        String exposedHeaders,
        long maxAge,
        String urlSource
) {
}
