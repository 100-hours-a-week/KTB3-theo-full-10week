package com.example.KTB_10WEEK.app.swagger.controller;

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
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "인증 API")
public interface AuthApiDoc {
    @Operation(summary = "로그인", description = "이메일과 비밀번호를 통해 사용자를 인증(토큰발급)합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 성공", value = """
                            {
                                "message": "Login Success",
                                "data": {
                                    "id": 1,
                                    "loginSuccess": true
                                },
                                "timestamp": "2025-10-12 20:05:15"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "로그인 실패. 유저를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 실패", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "사용자를 찾을 수 없습니다.",
                                "path": "/auth/login"
                            }
                            """)
            })),
    })

    public ResponseEntity<BaseResponse> login(@RequestBody @Valid LoginRequestDto request);

    @Operation(summary = "로그아웃", description = "회원의 접속상태를 로그아웃으로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그아웃 성공", value = """
                            {}
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> logout(HttpServletRequest request);
}
