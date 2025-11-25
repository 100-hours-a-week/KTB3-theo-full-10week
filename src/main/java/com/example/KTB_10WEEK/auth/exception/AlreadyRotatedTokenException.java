package com.example.KTB_10WEEK.auth.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class AlreadyRotatedTokenException extends BusinessException {
    private ErrorCode errorCode;
    public AlreadyRotatedTokenException() {
        super(ErrorCode.ALREADY_ROTATED_TOKEN);
    }
}
