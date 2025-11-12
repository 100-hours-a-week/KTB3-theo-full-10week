package com.example.KTB_7WEEK.app.exception.common;

import com.example.KTB_7WEEK.app.exception.handler.ErrorCode;

public class TooLargeImageException extends BusinessException{
    private ErrorCode errorCode;

    public TooLargeImageException() {
        super(ErrorCode.TOO_LARGE_IMAGE);
    }
}
