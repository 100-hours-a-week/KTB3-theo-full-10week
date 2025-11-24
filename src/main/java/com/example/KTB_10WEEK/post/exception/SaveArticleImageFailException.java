package com.example.KTB_10WEEK.post.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class SaveArticleImageFailException extends BusinessException {
    private ErrorCode errorCode;

    public SaveArticleImageFailException() {
        super(ErrorCode.SAVE_ARTICLE_IMAGE_FAIL);
    }
}
