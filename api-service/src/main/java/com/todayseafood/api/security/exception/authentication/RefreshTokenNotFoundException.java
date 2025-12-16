package com.todayseafood.api.security.exception.authentication;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class RefreshTokenNotFoundException extends JwtFilterCustomException {
    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
