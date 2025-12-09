package com.example.KTB_10WEEK.security.config;

import com.example.KTB_10WEEK.security.entrypoint.CustomAccessDeniedHandler;
import com.example.KTB_10WEEK.security.entrypoint.JwtAuthenticationEntryPoint;
import com.example.KTB_10WEEK.security.filter.JwtAuthenticationFilter;
import com.example.KTB_10WEEK.security.role.AdminRole;
import com.example.KTB_10WEEK.auth.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenService tokenService, ObjectMapper objectMapper) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenService);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(objectMapper);
        CustomAccessDeniedHandler jwtAccessDeniedHandler = new CustomAccessDeniedHandler(objectMapper);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(AdminRole.PERMITTED_URL).hasRole(AdminRole.ROLE)
                                .requestMatchers(HttpMethod.GET, PERMITTED_ALL_URL.get(HttpMethod.GET)).permitAll()
                                .requestMatchers(HttpMethod.POST, PERMITTED_ALL_URL.get(HttpMethod.POST)).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private static final Map<HttpMethod, String[]> PERMITTED_ALL_URL = Map.of(
            HttpMethod.POST, new String[]{
                    "/user",
                    "/auth/access/token",
                    "/auth/access/token/refresh",
                    "/user/email/double-check",
                    "/user/nickname/double-check"},
            HttpMethod.GET, new String[]{
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/swagger-ui/**",
                    "/api-docs/**"
            }

    );

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
