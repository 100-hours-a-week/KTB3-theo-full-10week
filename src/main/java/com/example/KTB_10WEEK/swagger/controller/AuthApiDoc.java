package com.example.KTB_10WEEK.swagger.controller;

import com.example.KTB_10WEEK.auth.dto.request.LoginRequestDto;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "인증 API")
public interface AuthApiDoc {
    @Operation(summary = "로그인", description = "이메일과 비밀번호를 통해 사용자를 인증(토큰발급)합니다.")
    public ResponseEntity<BaseResponse> login(@RequestBody @Valid LoginRequestDto request);

    @Operation(summary = "엑세스 토큰 재발급", description = "엑세스 토큰을 재발급 요청합니다.")
    public ResponseEntity<BaseResponse> refreshAccessToken(@CookieValue(name = "Refresh-Token") String refreshToken);

    @Operation(summary = "로그아웃", description = "회원의 접속상태를 로그아웃으로 변경합니다.")
    public ResponseEntity<BaseResponse> logout(HttpServletRequest request);
}
