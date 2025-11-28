package com.example.KTB_10WEEK.user.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class DeleteProfileImageFailException extends BusinessException {
    public DeleteProfileImageFailException() {
        super(ErrorCode.DELETE_PROFILE_IMAGE_FAIL);
    }
}
