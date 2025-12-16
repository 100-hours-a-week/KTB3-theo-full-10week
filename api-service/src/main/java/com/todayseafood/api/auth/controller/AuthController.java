package com.todayseafood.api.auth.controller;

import com.todayseafood.api.app.response.BaseResponse;
import com.todayseafood.api.app.response.ResponseMessage;
import com.todayseafood.api.auth.dto.request.LoginRequestDto;
import com.todayseafood.api.auth.dto.response.LoginWithTokenResponseDto;
import com.todayseafood.api.auth.dto.response.TokenPair;
import com.todayseafood.api.auth.service.AuthService;
import com.todayseafood.api.auth.service.cookie.RefreshTokenCookie;
import com.todayseafood.api.swagger.controller.AuthApiDoc;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApiDoc {
    private final AuthService authService;
    private final RefreshTokenCookie refreshTokenCookie;

    public AuthController(AuthService authService, RefreshTokenCookie refreshTokenCookie) {
        this.authService = authService;
        this.refreshTokenCookie = refreshTokenCookie;
    }

    @PostMapping("/access/token")
    public ResponseEntity<BaseResponse> login(@RequestBody
                                              @Valid LoginRequestDto request) {
        LoginWithTokenResponseDto result = authService.login(request);

        if (!result.isLoginSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(result.getLoginResponse());
        }

        ResponseCookie cookie = refreshTokenCookie.create(result.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + result.getAccessToken())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result.getLoginResponse());
    }

    @PostMapping("/access/token/refresh")
    public ResponseEntity<BaseResponse> refreshAccessToken(@CookieValue(name = "Refresh-Token") String refreshToken) {
        TokenPair tokenPair = authService.refreshTokens(refreshToken);

        ResponseCookie cookie = refreshTokenCookie.create(tokenPair.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenPair.getAccessToken())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new BaseResponse<>(ResponseMessage.TOKEN_REFRESH_SUCCESS));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse> logout() {
        authService.logout();
        ResponseCookie cookie = refreshTokenCookie.expire();
        System.out.println("ð"+cookie.toString());
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

}
