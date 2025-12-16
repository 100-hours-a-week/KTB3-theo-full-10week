package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class DeleteArticleImageFailException extends BusinessException {
    public DeleteArticleImageFailException() {
        super(ErrorCode.DELETE_ARTICLE_IMAGE_FAIL);
    }
}
