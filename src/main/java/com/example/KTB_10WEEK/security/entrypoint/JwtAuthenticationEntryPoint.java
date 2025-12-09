package com.example.KTB_10WEEK.security.entrypoint;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;
import com.example.KTB_10WEEK.app.exception.handler.ErrorResponseEntity;
import com.example.KTB_10WEEK.security.exception.authentication.JwtFilterCustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(response.isCommitted()) {
            return;
        }

        ErrorCode errorCode;

        if(authException instanceof JwtFilterCustomException jwtFilterCustomException) {
            errorCode = jwtFilterCustomException.getErrorCode();
        } else {
            errorCode = ErrorCode.UNAUTHORIZED;
        }

        int code = errorCode.getCode();
        HttpStatus status = errorCode.getStatus();
        String message = errorCode.getMessage();
        String path = request.getRequestURI();

        ErrorResponseEntity body = new ErrorResponseEntity(code, status, message, path);
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = objectMapper.writeValueAsString(body);
        response.getWriter().write(json);
    }
}
