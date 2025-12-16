package com.todayseafood.api.security.exception.authentication;

import com.todayseafood.api.app.exception.handler.ErrorCode;

public class AlreadyRotatedTokenException extends JwtFilterCustomException {
    public AlreadyRotatedTokenException() {
        super(ErrorCode.ALREADY_ROTATED_TOKEN);
    }
}
