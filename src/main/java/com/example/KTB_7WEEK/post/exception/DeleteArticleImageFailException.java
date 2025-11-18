package com.example.KTB_7WEEK.post.exception;

import com.example.KTB_7WEEK.app.exception.common.BusinessException;
import com.example.KTB_7WEEK.app.exception.handler.ErrorCode;

public class DeleteArticleImageFailException extends BusinessException {
    private ErrorCode errorCode;

    public DeleteArticleImageFailException() {
        super(ErrorCode.DELETE_ARTICLE_IMAGE_FAIL);
    }
}
