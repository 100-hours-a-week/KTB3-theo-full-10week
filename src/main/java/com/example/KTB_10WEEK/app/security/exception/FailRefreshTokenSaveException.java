package com.example.KTB_10WEEK.app.security.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class FailRefreshTokenSaveException extends GlobalFilterCustomException {
    private ErrorCode errorCode;

    public FailRefreshTokenSaveException() {
        super(ErrorCode.FAIL_REFRESH_TOKEN_SAVE);
    }
}
