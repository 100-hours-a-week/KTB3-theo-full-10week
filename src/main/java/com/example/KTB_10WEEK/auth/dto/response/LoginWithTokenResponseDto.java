package com.example.KTB_10WEEK.auth.dto.response;

import com.example.KTB_10WEEK.app.response.BaseResponse;

public class LoginWithTokenResponseDto {
    private final BaseResponse<LoginResponseDto> loginResponse;
    private final TokenPair tokenPair;

    public LoginWithTokenResponseDto(BaseResponse<LoginResponseDto> loginResponse, TokenPair tokenPair) {
        this.loginResponse = loginResponse;
        this.tokenPair = tokenPair;
    }

    public BaseResponse<LoginResponseDto> getLoginResponse() {
        return loginResponse;
    }

    public TokenPair getTokenPair() {
        return this.tokenPair;
    }

    public boolean isLoginSuccess() {
        LoginResponseDto response = this.loginResponse.getData();
        return response.isLoginSuccess();
    }

    public String getAccessToken() {
        return tokenPair.getAccessToken();
    }

    public String getRefreshToken() {
        return tokenPair.getRefreshToken();
    }
}
