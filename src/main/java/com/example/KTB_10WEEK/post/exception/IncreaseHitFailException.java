package com.example.KTB_10WEEK.post.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class IncreaseHitFailException extends BusinessException {
    public IncreaseHitFailException() {
        super(ErrorCode.INCREASE_HIT_FAIL);
    }
}
