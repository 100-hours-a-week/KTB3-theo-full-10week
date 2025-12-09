package com.example.KTB_10WEEK.security.exception.authentication;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class RefreshTokenNotFoundException extends JwtFilterCustomException {
    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
