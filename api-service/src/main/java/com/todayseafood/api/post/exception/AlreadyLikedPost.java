package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class AlreadyLikedPost extends BusinessException {
    public AlreadyLikedPost() {
        super(ErrorCode.ALREADY_LIKED_POST);
    }
}
