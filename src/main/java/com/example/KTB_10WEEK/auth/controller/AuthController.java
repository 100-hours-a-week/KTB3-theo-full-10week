package com.example.KTB_10WEEK.auth.controller;

import com.example.KTB_10WEEK.auth.dto.response.LoginResponseDto;
import com.example.KTB_10WEEK.auth.dto.response.LoginWithTokenResponseDto;
import com.example.KTB_10WEEK.auth.dto.response.TokenPair;
import com.example.KTB_10WEEK.auth.service.AuthService;
import com.example.KTB_10WEEK.app.swagger.controller.AuthApiDoc;
import com.example.KTB_10WEEK.auth.dto.request.LoginRequestDto;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApiDoc {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/access/token") // 로그인
    public ResponseEntity<BaseResponse> login(@RequestBody
                                              @Valid LoginRequestDto request) {
        LoginWithTokenResponseDto result = authService.login(request);

        if (!result.isLoginSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(result.getLoginResponse());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + result.getAccessToken())
                .header("X-Refresh-Token", result.getRefreshToken())
                .body(result.getLoginResponse());
    }

    @PostMapping("/access/token/refresh")
    public ResponseEntity<BaseResponse> refreshAccessToken(@RequestHeader(name = "X-Refresh-Token") String refreshToken) {
        TokenPair tokenPair = authService.refreshTokens(refreshToken);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokenPair.getAccessToken())
                .header("X-Refresh-Token", tokenPair.getRefreshToken()).build();
    }

    @PostMapping("/logout") // 로그아웃
    public ResponseEntity<BaseResponse> logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        BaseResponse response = authService.logout();

        return ResponseEntity.noContent().build();
    }

}
