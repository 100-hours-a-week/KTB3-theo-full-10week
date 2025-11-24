package com.example.KTB_10WEEK.post.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class InvalidPostTitleLengthException extends BusinessException {
    public InvalidPostTitleLengthException() {
        super(ErrorCode.POST_TITLE_LENGTH_OVER);
    }
}
