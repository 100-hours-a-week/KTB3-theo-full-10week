package com.example.KTB_7WEEK.post.exception;

import com.example.KTB_7WEEK.app.exception.common.BusinessException;
import com.example.KTB_7WEEK.app.exception.handler.ErrorCode;

import java.util.Base64;

public class AlreadyLikedPost extends BusinessException {
    private ErrorCode errorCode;

    public AlreadyLikedPost() {
        super(ErrorCode.ALREADY_LIKED_POST);
    }
}
