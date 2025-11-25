package com.example.KTB_10WEEK.app.config;

import com.example.KTB_10WEEK.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override // 이 필터 들어왔을 때 어떤 로직을 수행할 것인가? -> Jwt 토큰 인증해야지
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) { // JWT 토큰 없으면 필터로직 필요없음 다음 필터 동작
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = authorizationHeader.substring(7); // "Bearer " 이후 토큰 문자열

    }
}
