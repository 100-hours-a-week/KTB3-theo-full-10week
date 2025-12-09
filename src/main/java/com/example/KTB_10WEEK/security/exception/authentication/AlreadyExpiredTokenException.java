package com.example.KTB_10WEEK.security.exception.authentication;


import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class AlreadyExpiredTokenException extends JwtFilterCustomException {
    public AlreadyExpiredTokenException() {
        super(ErrorCode.ALREADY_EXPIRED_TOKEN);
    }
}
