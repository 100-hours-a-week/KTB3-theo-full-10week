package com.example.KTB_10WEEK.auth.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class NotJwtTokenException extends BusinessException {
    private ErrorCode errorCode;

    public NotJwtTokenException() {
        super(ErrorCode.NOT_JWT_TOKEN);
    }
}
