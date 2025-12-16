package com.todayseafood.api.app.exception.common;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class TooLargeImageException extends BusinessException{
    public TooLargeImageException() {
        super(ErrorCode.TOO_LARGE_IMAGE);
    }
}
