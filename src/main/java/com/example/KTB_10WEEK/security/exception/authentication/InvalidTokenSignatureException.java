package com.example.KTB_10WEEK.security.exception.authentication;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class InvalidTokenSignatureException extends JwtFilterCustomException {
    public InvalidTokenSignatureException() {
        super(ErrorCode.INVALID_TOKEN_SIGNATURE);
    }
}
