package com.example.KTB_10WEEK.user.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class WhiteSpaceNotAllowedException extends BusinessException {
    public WhiteSpaceNotAllowedException() {
        super(ErrorCode.WHITESPACE_NOT_ALLOWED);
    }
}
