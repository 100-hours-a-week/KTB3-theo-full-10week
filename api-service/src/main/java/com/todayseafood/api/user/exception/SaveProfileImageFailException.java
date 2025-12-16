package com.todayseafood.api.user.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class SaveProfileImageFailException extends BusinessException {
    public SaveProfileImageFailException() {
        super(ErrorCode.SAVE_PROFILE_IMAGE_FAIL);
    }
}
