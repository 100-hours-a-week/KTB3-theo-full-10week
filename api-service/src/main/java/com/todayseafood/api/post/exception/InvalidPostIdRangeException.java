package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;


public class InvalidPostIdRangeException extends BusinessException {
    public InvalidPostIdRangeException() {
        super(ErrorCode.INVALID_POST_ID);
    }
}
