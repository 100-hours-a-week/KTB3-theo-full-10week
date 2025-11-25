package com.example.KTB_10WEEK.app.security.config;

import com.example.KTB_10WEEK.app.security.property.CorsProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(CorsProperty corsProperty) {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(corsProperty.allowedOrigins().split(",")));
        config.setAllowedMethods(List.of(corsProperty.allowedMethods().split(",")));
        config.setAllowedHeaders(List.of(corsProperty.allowedHeaders().split(",")));
        config.setExposedHeaders(List.of(corsProperty.exposedHeaders().split(",")));
        config.setAllowCredentials(true);
        config.setMaxAge(corsProperty.maxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProperty.urlSource(), config);
        return source;
    }
}
