package com.example.KTB_10WEEK.app.security.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class FailTokenExpireException extends GlobalFilterCustomException {
    private ErrorCode errorCode;

    public FailTokenExpireException() {
        super(ErrorCode.FAIL_TOKEN_EXPIRE);
    }
}
