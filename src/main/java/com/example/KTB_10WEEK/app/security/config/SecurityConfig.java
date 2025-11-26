package com.example.KTB_10WEEK.app.security.config;

import com.example.KTB_10WEEK.app.security.filter.GlobalFilterCustomExceptionFilter;
import com.example.KTB_10WEEK.app.security.filter.JwtAuthenticationFilter;
import com.example.KTB_10WEEK.auth.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenService tokenService, ObjectMapper objectMapper) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenService);
        GlobalFilterCustomExceptionFilter globalFilterCustomExceptionFilter = new GlobalFilterCustomExceptionFilter(objectMapper);

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // CORS 설정 빈 주입
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/auth/access/token").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/email/double-check").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/nickname/double-check").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(globalFilterCustomExceptionFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
