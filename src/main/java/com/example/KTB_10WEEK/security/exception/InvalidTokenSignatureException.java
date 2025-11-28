package com.example.KTB_10WEEK.security.exception;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class InvalidTokenSignatureException extends GlobalFilterCustomException {
    private ErrorCode errorCode;

    public InvalidTokenSignatureException() {
        super(ErrorCode.INVALID_TOKEN_SIGNATURE);
    }
}
