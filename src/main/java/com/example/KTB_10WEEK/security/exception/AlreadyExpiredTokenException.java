package com.example.KTB_10WEEK.security.exception;


import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class AlreadyExpiredTokenException extends GlobalFilterCustomException {
    public AlreadyExpiredTokenException() {
        super(ErrorCode.ALREADY_EXPIRED_TOKEN);
    }
}
