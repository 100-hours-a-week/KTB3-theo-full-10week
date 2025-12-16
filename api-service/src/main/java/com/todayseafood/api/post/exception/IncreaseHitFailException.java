package com.todayseafood.api.post.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class IncreaseHitFailException extends BusinessException {
    public IncreaseHitFailException() {
        super(ErrorCode.INCREASE_HIT_FAIL);
    }
}
