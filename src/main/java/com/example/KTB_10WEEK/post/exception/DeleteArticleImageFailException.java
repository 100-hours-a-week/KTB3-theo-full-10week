package com.example.KTB_10WEEK.post.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class DeleteArticleImageFailException extends BusinessException {
    public DeleteArticleImageFailException() {
        super(ErrorCode.DELETE_ARTICLE_IMAGE_FAIL);
    }
}
