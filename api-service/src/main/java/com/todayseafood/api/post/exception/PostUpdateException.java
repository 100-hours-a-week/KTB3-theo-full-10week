package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class PostUpdateException extends BusinessException {

    public PostUpdateException() {
        super(ErrorCode.POST_UPDATE_ERROR);
    }

}
