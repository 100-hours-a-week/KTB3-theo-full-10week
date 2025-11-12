package com.example.KTB_7WEEK.user.exception;

import com.example.KTB_7WEEK.app.exception.common.BusinessException;
import com.example.KTB_7WEEK.app.exception.handler.ErrorCode;

public class SaveProfileImageFailException extends BusinessException {
    private ErrorCode errorCode;

    public SaveProfileImageFailException() {
        super(ErrorCode.SAVE_PROFILE_IMAGE_FAIL);
    }
}
