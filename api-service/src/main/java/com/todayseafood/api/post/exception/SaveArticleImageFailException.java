package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class SaveArticleImageFailException extends BusinessException {
    public SaveArticleImageFailException() {
        super(ErrorCode.SAVE_ARTICLE_IMAGE_FAIL);
    }
}
