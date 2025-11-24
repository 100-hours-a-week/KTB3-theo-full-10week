package com.example.KTB_10WEEK.user.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }

}
