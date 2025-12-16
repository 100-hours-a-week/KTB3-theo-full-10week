package com.todayseafood.api.app.exception.common;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class ImageNotFoundException extends BusinessException{
    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }
}
