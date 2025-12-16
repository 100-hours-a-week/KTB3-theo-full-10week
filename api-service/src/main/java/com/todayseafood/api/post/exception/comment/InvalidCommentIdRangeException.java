package com.todayseafood.api.post.exception.comment;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class InvalidCommentIdRangeException extends BusinessException {
    public InvalidCommentIdRangeException() {
        super(ErrorCode.INVALID_POST_ID);
    }
}
