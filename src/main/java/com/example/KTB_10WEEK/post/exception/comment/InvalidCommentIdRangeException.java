package com.example.KTB_10WEEK.post.exception.comment;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class InvalidCommentIdRangeException extends BusinessException {
    public InvalidCommentIdRangeException() {
        super(ErrorCode.INVALID_POST_ID);
    }
}
