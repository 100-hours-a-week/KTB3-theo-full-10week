package com.example.KTB_10WEEK.security.exception;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class AlreadyRotatedTokenException extends GlobalFilterCustomException {
    private ErrorCode errorCode;
    public AlreadyRotatedTokenException() {
        super(ErrorCode.ALREADY_ROTATED_TOKEN);
    }
}
