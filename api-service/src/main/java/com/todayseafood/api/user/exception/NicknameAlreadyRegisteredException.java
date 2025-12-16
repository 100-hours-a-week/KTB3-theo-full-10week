package com.todayseafood.api.user.exception;

import com.todayseafood.api.app.exception.common.BusinessException;
import com.todayseafood.api.app.exception.handler.ErrorCode;

public class NicknameAlreadyRegisteredException extends BusinessException {
    public NicknameAlreadyRegisteredException() {
        super(ErrorCode.NICKNAME_ALREADY_REGISTERED);
    }

}
