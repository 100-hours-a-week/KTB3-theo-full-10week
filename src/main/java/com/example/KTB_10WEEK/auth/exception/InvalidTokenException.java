package com.example.KTB_10WEEK.auth.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class InvalidTokenException extends BusinessException {
    private ErrorCode errorCode;

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
