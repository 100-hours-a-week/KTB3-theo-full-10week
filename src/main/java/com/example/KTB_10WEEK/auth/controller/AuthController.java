package com.example.KTB_10WEEK.auth.controller;

import com.example.KTB_10WEEK.auth.dto.response.LoginWithTokenResponseDto;
import com.example.KTB_10WEEK.auth.dto.response.TokenPair;
import com.example.KTB_10WEEK.auth.service.AuthService;
import com.example.KTB_10WEEK.swagger.controller.AuthApiDoc;
import com.example.KTB_10WEEK.auth.dto.request.LoginRequestDto;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import com.example.KTB_10WEEK.auth.service.cookie.RefreshTokenCookie;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/access/token") // 로그인
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
                .build();
    }

    @PostMapping("/logout") // 로그아웃
    public ResponseEntity<BaseResponse> logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        BaseResponse response = authService.logout();

        ResponseCookie cookie = refreshTokenCookie.expire();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

}
