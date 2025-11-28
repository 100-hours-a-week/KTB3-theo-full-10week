package com.example.KTB_10WEEK.user.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class UserDeleteException extends BusinessException {
    public UserDeleteException() {
        super(ErrorCode.USER_DELETE_ERROR);
    }

}
