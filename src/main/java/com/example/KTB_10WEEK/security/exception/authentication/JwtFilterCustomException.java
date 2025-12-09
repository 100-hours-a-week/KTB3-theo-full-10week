package com.example.KTB_10WEEK.security.exception.authentication;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;
import org.springframework.security.core.AuthenticationException;

public class JwtFilterCustomException extends AuthenticationException {
    private final ErrorCode errorCode;

    public JwtFilterCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
