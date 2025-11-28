package com.example.KTB_10WEEK.post.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class AlreadyLikedPost extends BusinessException {
    public AlreadyLikedPost() {
        super(ErrorCode.ALREADY_LIKED_POST);
    }
}
