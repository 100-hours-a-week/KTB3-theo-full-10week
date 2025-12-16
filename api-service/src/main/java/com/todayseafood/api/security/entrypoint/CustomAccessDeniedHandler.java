package com.todayseafood.api.security.entrypoint;

import com.todayseafood.api.app.exception.handler.ErrorCode;
import com.todayseafood.api.app.exception.handler.ErrorResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if(response.isCommitted()) {
            return;
        }

        ErrorCode errorCode = ErrorCode.FORBIDDEN;

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
