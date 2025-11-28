package com.example.KTB_10WEEK.user.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class UserUpdateException extends BusinessException {
    public UserUpdateException() {
        super(ErrorCode.USER_UPDATE_ERROR);
    }
}
