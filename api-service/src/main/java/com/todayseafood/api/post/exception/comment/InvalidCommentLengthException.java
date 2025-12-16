package com.todayseafood.api.post.exception.comment;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class InvalidCommentLengthException extends BusinessException {
    public InvalidCommentLengthException() {
        super(ErrorCode.NO_COMMENT_NOT_ALLOWED);
    }
}
