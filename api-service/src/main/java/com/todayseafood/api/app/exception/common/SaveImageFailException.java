package com.todayseafood.api.app.exception.common;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class SaveImageFailException extends BusinessException{
    public SaveImageFailException() {
        super(ErrorCode.SAVE_IMAGE_FAIL);
    }
}
