package com.example.KTB_7WEEK.auth.controller;

import com.example.KTB_7WEEK.auth.dto.response.LoginResponseDto;
import com.example.KTB_7WEEK.auth.service.AuthService;
import com.example.KTB_7WEEK.swagger.controller.AuthApiDoc;
import com.example.KTB_7WEEK.auth.dto.request.LoginRequestDto;
import com.example.KTB_7WEEK.app.response.BaseResponse;
import com.example.KTB_7WEEK.auth.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApiDoc {
    private final AuthService authService;
    private final TokenService tokenService;
    @Value("${application.auth.token.expire}")
    private int tokenExpireMin;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/access/token") // 로그인
    public ResponseEntity<BaseResponse> login(@RequestBody
                                              @Valid LoginRequestDto request) {
        BaseResponse response = authService.login(request);
        boolean isLoginSuccess = ((LoginResponseDto) response.getData()).isLoginSuccess();
        if (isLoginSuccess) {
            String token = tokenService.issue(Duration.ofMinutes(tokenExpireMin));
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Authorization", "Bearer" + token)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/logout") // 로그아웃
    public ResponseEntity<BaseResponse> logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        tokenService.expire(authorization);
        BaseResponse response = authService.logout();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}
