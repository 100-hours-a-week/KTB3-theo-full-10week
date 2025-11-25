package com.example.KTB_10WEEK.auth.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class FailRefreshTokenSaveException extends BusinessException {
    private ErrorCode errorCode;

    public FailRefreshTokenSaveException() {
        super(ErrorCode.FAIL_REFRESH_TOKEN_SAVE);
    }
}
