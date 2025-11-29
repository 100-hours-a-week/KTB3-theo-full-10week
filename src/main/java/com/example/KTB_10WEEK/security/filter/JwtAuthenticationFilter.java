package com.example.KTB_10WEEK.security.filter;

import com.example.KTB_10WEEK.security.exception.GlobalFilterCustomException;
import com.example.KTB_10WEEK.security.principal.UserPrincipal;
import com.example.KTB_10WEEK.security.role.RoleConfig;
import com.example.KTB_10WEEK.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String accessToken = authorizationHeader.substring(7);

            Map<String, Object> payload = tokenService.verifyAndGetPayload(accessToken);
            Long userId = ((Number) payload.get("userId")).longValue();
            String role = (String) payload.get("role");
            List<SimpleGrantedAuthority> authorityList = RoleConfig.from(role).getAllAuthorityList();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    new UserPrincipal(userId, role),
                    null,
                    authorityList
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
        return path.startsWith("/auth/access/token") || path.startsWith("/auth/access/token/refresh");
    }
}
