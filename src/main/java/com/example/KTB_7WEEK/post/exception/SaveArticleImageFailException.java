package com.example.KTB_7WEEK.post.exception;

import com.example.KTB_7WEEK.app.exception.common.BusinessException;
import com.example.KTB_7WEEK.app.exception.handler.ErrorCode;

public class SaveArticleImageFailException extends BusinessException {
    private ErrorCode errorCode;

    public SaveArticleImageFailException() {
        super(ErrorCode.SAVE_ARTICLE_IMAGE_FAIL);
    }
}
