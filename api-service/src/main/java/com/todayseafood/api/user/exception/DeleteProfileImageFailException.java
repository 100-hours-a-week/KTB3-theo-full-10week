package com.todayseafood.api.user.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class DeleteProfileImageFailException extends BusinessException {
    public DeleteProfileImageFailException() {
        super(ErrorCode.DELETE_PROFILE_IMAGE_FAIL);
    }
}
