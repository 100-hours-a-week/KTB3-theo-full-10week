package com.example.KTB_10WEEK.post.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class IncreaseLikeFailException extends BusinessException {
    public IncreaseLikeFailException() {
        super(ErrorCode.INCREASE_LIKE_FAIL);
    }
}
