package com.example.KTB_10WEEK.auth.interceptor;

import com.example.KTB_10WEEK.auth.exception.UnAuthorizedException;
import com.example.KTB_10WEEK.auth.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@ConfigurationProperties(prefix = "application.auth")
public class TokenAuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    public TokenAuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            tokenService.verify(token).orElseThrow(() -> new UnAuthorizedException());
//            request.setAttribute("verifyToken", verifyToken);
            return true;
        } else {
            throw new UnAuthorizedException();
        }
    }
}
