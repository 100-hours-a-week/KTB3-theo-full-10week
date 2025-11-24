package com.example.KTB_10WEEK.app.exception.common;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class ImageNotFoundException extends BusinessException{
    private ErrorCode errorCode;

    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }
}
