package com.todayseafood.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.List;

@Configuration
public class GatewayCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter(CorsProperty corsProperty) {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(corsProperty.getAllowedOrigins().split(",")));
        config.setAllowedMethods(List.of(corsProperty.getAllowedMethods().split(",")));
        config.setAllowedHeaders(List.of(corsProperty.getAllowedHeaders().split(",")));
        config.setExposedHeaders(List.of(corsProperty.getExposedHeaders().split(",")));
        config.setAllowCredentials(true);
        config.setMaxAge(corsProperty.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProperty.getUrlSource(), config);
        return new CorsWebFilter(source);
    }

}
