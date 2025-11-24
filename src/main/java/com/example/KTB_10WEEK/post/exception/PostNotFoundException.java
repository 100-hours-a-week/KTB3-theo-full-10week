package com.example.KTB_10WEEK.post.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
