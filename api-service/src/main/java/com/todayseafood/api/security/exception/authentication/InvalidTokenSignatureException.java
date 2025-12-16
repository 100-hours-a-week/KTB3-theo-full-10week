package com.todayseafood.api.security.exception.authentication;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class InvalidTokenSignatureException extends JwtFilterCustomException {
    public InvalidTokenSignatureException() {
        super(ErrorCode.INVALID_TOKEN_SIGNATURE);
    }
}
