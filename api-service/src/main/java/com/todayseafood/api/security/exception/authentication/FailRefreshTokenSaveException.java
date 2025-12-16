package com.todayseafood.api.security.exception.authentication;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class FailRefreshTokenSaveException extends JwtFilterCustomException {
    public FailRefreshTokenSaveException() {
        super(ErrorCode.FAIL_REFRESH_TOKEN_SAVE);
    }
}
