package com.example.KTB_10WEEK.security.exception.authentication;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class FailRefreshTokenSaveException extends JwtFilterCustomException {
    public FailRefreshTokenSaveException() {
        super(ErrorCode.FAIL_REFRESH_TOKEN_SAVE);
    }
}
