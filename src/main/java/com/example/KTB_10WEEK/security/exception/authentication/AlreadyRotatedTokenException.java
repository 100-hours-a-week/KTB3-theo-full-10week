package com.example.KTB_10WEEK.security.exception.authentication;

import com.example.KTB_10WEEK.app.exception.handler.ErrorCode;

public class AlreadyRotatedTokenException extends JwtFilterCustomException {
    public AlreadyRotatedTokenException() {
        super(ErrorCode.ALREADY_ROTATED_TOKEN);
    }
}
