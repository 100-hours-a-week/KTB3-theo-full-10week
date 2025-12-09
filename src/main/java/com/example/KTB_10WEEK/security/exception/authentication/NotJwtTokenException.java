package com.example.KTB_10WEEK.security.exception.authentication;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class NotJwtTokenException extends JwtFilterCustomException {
    public NotJwtTokenException() {
        super(ErrorCode.NOT_JWT_TOKEN);
    }
}
