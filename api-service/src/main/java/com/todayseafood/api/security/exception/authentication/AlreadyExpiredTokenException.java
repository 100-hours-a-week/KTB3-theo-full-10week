package com.todayseafood.api.security.exception.authentication;


import com.todayseafood.api.app.exception.handler.ErrorCode;

public class AlreadyExpiredTokenException extends JwtFilterCustomException {
    public AlreadyExpiredTokenException() {
        super(ErrorCode.ALREADY_EXPIRED_TOKEN);
    }
}
