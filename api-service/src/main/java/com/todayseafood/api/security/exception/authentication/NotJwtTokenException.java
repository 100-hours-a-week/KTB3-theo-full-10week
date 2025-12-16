package com.todayseafood.api.security.exception.authentication;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class NotJwtTokenException extends JwtFilterCustomException {
    public NotJwtTokenException() {
        super(ErrorCode.NOT_JWT_TOKEN);
    }
}
