package com.example.KTB_10WEEK.security.exception;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class NotJwtTokenException extends GlobalFilterCustomException {
    private ErrorCode errorCode;

    public NotJwtTokenException() {
        super(ErrorCode.NOT_JWT_TOKEN);
    }
}
