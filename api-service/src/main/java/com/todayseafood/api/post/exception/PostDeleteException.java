package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class PostDeleteException extends BusinessException {

    public PostDeleteException() {
        super(ErrorCode.POST_DELETE_ERROR);
    }

}
