package com.example.KTB_10WEEK.auth.exception;


import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class AlreadyExpiredTokenException extends BusinessException {
    private ErrorCode errorCode;

    public AlreadyExpiredTokenException() {
        super(ErrorCode.ALREADY_EXPIRED_TOKEN);
    }
}
