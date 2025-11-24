package com.example.KTB_10WEEK.user.exception;

import com.example.KTB_10WEEK.app.exception.common.BusinessException;
import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class NicknameUpdateException extends BusinessException {
    public NicknameUpdateException() {
        super(ErrorCode.NICKNAME_UPDATE_ERROR);
    }
}
