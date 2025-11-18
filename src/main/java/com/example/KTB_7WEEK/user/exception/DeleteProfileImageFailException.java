package com.example.KTB_7WEEK.user.exception;

import com.example.KTB_7WEEK.app.exception.common.BusinessException;
import com.example.KTB_7WEEK.app.exception.handler.ErrorCode;

public class DeleteProfileImageFailException extends BusinessException {
    private ErrorCode errorCode;

    public DeleteProfileImageFailException() {
        super(ErrorCode.DELETE_PROFILE_IMAGE_FAIL);
    }
}
