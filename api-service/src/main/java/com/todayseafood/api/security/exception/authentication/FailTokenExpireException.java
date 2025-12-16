package com.todayseafood.api.security.exception.authentication;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class FailTokenExpireException extends JwtFilterCustomException {
    public FailTokenExpireException() {
        super(ErrorCode.FAIL_TOKEN_EXPIRE);
    }
}
