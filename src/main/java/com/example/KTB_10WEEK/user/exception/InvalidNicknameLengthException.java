package com.example.KTB_10WEEK.user.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class InvalidNicknameLengthException extends BusinessException {
    public InvalidNicknameLengthException() {
        super(ErrorCode.INVALID_NICKNAME_LENGTH);
    }

}
