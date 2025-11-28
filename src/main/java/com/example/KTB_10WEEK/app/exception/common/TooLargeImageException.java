package com.example.KTB_10WEEK.app.exception.common;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class TooLargeImageException extends BusinessException{
    public TooLargeImageException() {
        super(ErrorCode.TOO_LARGE_IMAGE);
    }
}
