package com.example.KTB_10WEEK.security.exception;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class GlobalFilterCustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public GlobalFilterCustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
