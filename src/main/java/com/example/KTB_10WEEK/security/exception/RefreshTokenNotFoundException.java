package com.example.KTB_10WEEK.security.exception;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class RefreshTokenNotFoundException extends GlobalFilterCustomException {
    private ErrorCode errorCode;

    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
