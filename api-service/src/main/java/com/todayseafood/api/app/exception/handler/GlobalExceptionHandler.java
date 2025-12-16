package com.todayseafood.api.app.exception.handler;

import com.todayseafood.api.app.exception.common.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseEntity> handleBusinessException(BusinessException e, HttpServletRequest req) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode(), req.getRequestURI());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<FieldErrorDto> handleBindException(BindException e, HttpServletRequest req) {
        return ErrorResponseEntity.toResponseEntity(HttpStatus.BAD_REQUEST, e.getFieldErrors(), req.getRequestURI());
    }
}
