package com.example.KTB_10WEEK.app.security.filter;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;
import com.example.KTB_10WEEK.app.exception.handler.ErrorResponseEntity;
import com.example.KTB_10WEEK.app.security.exception.GlobalFilterCustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalFilterCustomExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public GlobalFilterCustomExceptionFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (GlobalFilterCustomException e) {
            processException(request, response, e.getErrorCode());
        }
    }

    private void processException(HttpServletRequest request, HttpServletResponse response, ErrorCode errorCode) throws IOException {
        if (response.isCommitted()) {
            return;
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
