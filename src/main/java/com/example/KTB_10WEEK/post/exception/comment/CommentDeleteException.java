package com.example.KTB_10WEEK.post.exception.comment;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class CommentDeleteException extends BusinessException {

    public CommentDeleteException() {
        super(ErrorCode.COMMENT_DELETE_ERROR);
    }

}
