package com.example.KTB_10WEEK.post.exception.comment;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class CommentCreateException extends BusinessException {

    public CommentCreateException() {
        super(ErrorCode.COMMENT_CREATE_ERROR);
    }

}
