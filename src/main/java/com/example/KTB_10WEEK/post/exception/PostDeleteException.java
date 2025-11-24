package com.example.KTB_10WEEK.post.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class PostDeleteException extends BusinessException {

    public PostDeleteException() {
        super(ErrorCode.POST_DELETE_ERROR);
    }

}
