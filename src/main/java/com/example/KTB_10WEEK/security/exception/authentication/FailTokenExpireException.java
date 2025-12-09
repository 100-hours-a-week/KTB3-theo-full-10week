package com.example.KTB_10WEEK.security.exception.authentication;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class FailTokenExpireException extends JwtFilterCustomException {
    public FailTokenExpireException() {
        super(ErrorCode.FAIL_TOKEN_EXPIRE);
    }
}
