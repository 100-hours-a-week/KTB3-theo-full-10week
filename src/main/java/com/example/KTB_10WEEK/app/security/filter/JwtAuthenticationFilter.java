package com.example.KTB_10WEEK.app.security.filter;

import com.example.KTB_10WEEK.app.security.exception.GlobalFilterCustomException;
import com.example.KTB_10WEEK.app.security.principal.UserPrincipal;
import com.example.KTB_10WEEK.app.security.role.RoleConfig;
import com.example.KTB_10WEEK.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        try {
            String accessToken = authorizationHeader.substring(7); // "Bearer " 이후 토큰 문자열

            Map<String, Object> payload = tokenService.verifyAndGetPayload(accessToken);
            Long userId = ((Number) payload.get("userId")).longValue();
            String role = (String) payload.get("role");
            List<SimpleGrantedAuthority> authorityList = RoleConfig.from(role).getAllAuthorityList();

            UserPrincipal principal = new UserPrincipal(userId, role);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal, // 누가
                    null, // 토큰값
                    authorityList // 권한 목록
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (GlobalFilterCustomException e) {
            SecurityContextHolder.clearContext();
            throw e;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth");
    }
}
