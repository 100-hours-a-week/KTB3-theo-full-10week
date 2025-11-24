package com.example.KTB_10WEEK.auth.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class FailTokenExpireException extends BusinessException {
    private ErrorCode errorCode;

    public FailTokenExpireException() {
        super(ErrorCode.FAIL_TOKEN_EXPIRE);
    }
}
