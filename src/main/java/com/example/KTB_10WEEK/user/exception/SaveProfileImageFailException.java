package com.example.KTB_10WEEK.user.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class SaveProfileImageFailException extends BusinessException {
    public SaveProfileImageFailException() {
        super(ErrorCode.SAVE_PROFILE_IMAGE_FAIL);
    }
}
