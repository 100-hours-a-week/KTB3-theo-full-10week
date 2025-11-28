package com.example.KTB_10WEEK.security.exception;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class FailRefreshTokenSaveException extends GlobalFilterCustomException {
    public FailRefreshTokenSaveException() {
        super(ErrorCode.FAIL_REFRESH_TOKEN_SAVE);
    }
}
