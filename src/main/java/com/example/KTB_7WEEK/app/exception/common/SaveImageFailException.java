package com.example.KTB_7WEEK.app.exception.common;

import com.example.KTB_7WEEK.app.exception.handler.ErrorCode;

public class SaveImageFailException extends BusinessException{
    private ErrorCode errorCode;

    public SaveImageFailException() {
        super(ErrorCode.SAVE_IMAGE_FAIL);
    }
}
