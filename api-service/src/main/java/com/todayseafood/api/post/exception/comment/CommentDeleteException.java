package com.todayseafood.api.post.exception.comment;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class CommentDeleteException extends BusinessException {

    public CommentDeleteException() {
        super(ErrorCode.COMMENT_DELETE_ERROR);
    }

}
