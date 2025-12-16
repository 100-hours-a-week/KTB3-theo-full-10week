package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class PostCreateException extends BusinessException {

    public PostCreateException() {
        super(ErrorCode.POST_CREATE_ERROR);
    }
}
