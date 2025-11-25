package com.example.KTB_10WEEK.app.security.exception;


import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class AlreadyExpiredTokenException extends GlobalFilterCustomException {
    private ErrorCode errorCode;

    public AlreadyExpiredTokenException() {
        super(ErrorCode.ALREADY_EXPIRED_TOKEN);
    }
}
