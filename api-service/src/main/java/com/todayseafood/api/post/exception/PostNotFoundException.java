package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
