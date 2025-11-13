package com.example.KTB_7WEEK.app.swagger.controller.user;

import com.example.KTB_7WEEK.app.response.BaseResponse;
import com.example.KTB_7WEEK.user.dto.request.*;
import com.example.KTB_7WEEK.user.dto.response.EditProfileResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Public User", description = "회원 API")
public interface UserApiDoc {
    @Operation(summary = "회원정보 조회", description = "회원 PK를 통해 특정 회원을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원정보 조회 성공", value = """
                            { 
                                "message": "UserInfo Load Success",
                                "data": {
                                    "id": 1,
                                    "email": "test@test.com",
                                    "nickname": "startup",
                                    "profile_image": null,
                                    "created_at": "2025-10-12 20:03:59",
                                    "updated_at": "2025-10-12 20:03:59"
                                },
                                "timestamp": "2025-10-12 20:13:14"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "401", description = "로그인 인증이 필요합니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 인증이 필요합니다.", value = """
                            { // 맞는디..? 
                                 "code": 401,
                                 "status": "UNAUTHORIZED",
                                 "message": "로그인 인증이 필요합니다.",
                                 "path": "/user/3"
                             }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원정보를 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "사용자를 찾을 수 없습니다.",
                                "path": "/user/3"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> findByPublicUserId(@PathVariable
                                                           @NotNull
                                                           @Positive Long userId);

    @Operation(summary = "회원가입", description = "이메일/비밀번호/닉네임/프로필이미지를 입력받아 새로운 회원을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 완료"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원 생성 완료", value = """
                            {
                                "message": "User Register Success",
                                "data": {
                                    "id": 1
                                },
                                "timestamp": "2025-10-12 19:54:28"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "이메일 형식 불일치", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "이메일 형식 불일치(영 대소문자, 숫자만 가능합니다) ex) test@test.com",
                                "path": "/user"
                            }
                            """),
                    @ExampleObject(name = "비밀번호 형식 불일치", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "비밀번호 형식 불일치(8자 이상 20자 이하 / 대문자, 소문자, 숫자, 특수문자 각각 1개씩 포함해야 합니다.",
                                "path": "/user"
                            }
                            """),
                    @ExampleObject(name = "닉네임 길이 제한", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "닉네임은 10글자 이내 입니다.",
                                "path": "/user"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 리소스입니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "이미 등록된 이메일", value = """
                            {
                                "code": 409,
                                "status": "CONFLICT",
                                "message": "이미 등록된 이메일 입니다.",
                                "path": "/user"
                            }
                            """),
                    @ExampleObject(name = "이미 등록된 닉네임", value = """
                            {
                                 "code": 409,
                                 "status": "CONFLICT",
                                 "message": "이미 등록된 닉네임 입니다.",
                                 "path": "/user"
                             }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> createPublicUser(@RequestBody @Valid RegistUserRequestDto request);

    @Operation(summary = "닉네임 중복 확인", description = "사용중인 닉네임인지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임 사용 여부 확인(true/false)"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "닉네임 사용 가능", value = """
                            {
                                "message": "Nickname is Available",
                                "data": {
                                    "nickname": "nickname",
                                    "available": true
                                },
                                "timestamp": "2025-10-12 20:19:07"
                            }
                            """),
                    @ExampleObject(name = "닉네임 사용 불가능", value = """
                            {
                                "message": "Nickname is Not Available",
                                "data": {
                                    "nickname": "startup",
                                    "available": false
                                },
                                "timestamp": "2025-10-12 20:20:53"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "닉네임 길이 제한", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "닉네임은 10글자 이내 입니다.",
                                "path": "/user/nickname/double-check"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> doubleCheckNickname(@RequestBody
                                                            @Valid CheckNicknameAvailabilityRequestDto request);

    @Operation(summary = "이메일 중복 확인", description = "사용중인 이메일인지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 사용 여부 확인(true/false)"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "이메일 사용 가능", value = """
                            {
                                "message": "Email is Available",
                                "data": {
                                    "email": "testtest@test.com",
                                    "available": true
                                },
                                "timestamp": "2025-10-13 15:50:20"
                            }
                            """),
                    @ExampleObject(name = "이메일 사용 불가능", value = """
                            {
                                "message": "Email is Not Available",
                                "data": {
                                    "email": "test@test.com",
                                    "available": false
                                },
                                "timestamp": "2025-10-13 15:49:27"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "이메일 형식 불일치", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "이메일 형식 불일치(영 대소문자, 숫자만 가능합니다) ex) test@test.com",
                                "path": "/user/email/double-check"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> doubleCheckEmail(@RequestBody
                                                         @Valid CheckEmailAvailabilityRequestDto request);

    @Operation(summary = "회원 프로필 수정", description = "특정 회원의 PK를 통해 프로필 이미지, 닉네임을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 완료"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원 프로필 수정", value = """
                            {
                                "message" : "Profile_Edit_Success",
                                "data" : {
                                    "nickname" : "startup",
                                    "profile_image" : "xxx",
                                    "updatedAt" : "2025-09-01 00:00:00"
                                }
                            }
                            """)
            })),
            @ApiResponse(responseCode = "401", description = "로그인 인증이 필요합니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 인증이 필요합니다.", value = """
                            {
                                 "code": 401,
                                 "status": "UNAUTHORIZED",
                                 "message": "로그인 인증이 필요합니다.",
                                 "path": "/user/3"
                             }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "회원을 찾을 수 없습니다.",
                                "path": "/user/3/password"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> editProfile(@PathVariable("userId")
                                                    @NotNull
                                                    @Positive Long userId,
                                                    @Valid
                                                    @ModelAttribute EditProfileRequestDto request);

    @Operation(summary = "비밀번호 수정", description = "특정 회원의 PK를 통해 비밀번호를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 완료"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "비밀번호 수정 성공", value = """
                            {
                                "message" : "Password_Change_Success",
                                "data" : {
                                    "user_id" : 1,
                                     "email" : "test@startupcode.kr",
                                    "nickname" : "startup",
                                    "profile_image" : "xxx",
                                    "createdAt" : "2025-09-01 00:00:00",
                                    "updatedAt" : "2025-09-01 00:00:00"
                                }
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "비밀번호 형식 불일치", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "비밀번호 형식 불일치(8자 이상 20자 이하 / 대문자, 소문자, 숫자, 특수문자 각각 1개씩 포함해야 합니다.",
                                "path": "/user/1/password"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "401", description = "로그인 인증이 필요합니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 인증이 필요합니다.", value = """
                            {
                                 "code": 401,
                                 "status": "UNAUTHORIZED",
                                 "message": "로그인 인증이 필요합니다.",
                                 "path": "/user/3"
                             }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "회원을 찾을 수 없습니다.",
                                "path": "/user/3/password"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> changePassword(@PathVariable("userId")
                                                       @NotNull
                                                       @Positive Long userId,
                                                       @RequestBody
                                                       @Valid PasswordChangeRequestDto request);


    @Operation(summary = "닉네임 수정", description = "특정 회원의 PK 닉네임을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 완료"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "닉네임 수정 완료", value = """
                            {
                                "message": "Nickname Edit Success",
                                "data": {
                                    "id": 1,
                                    "nickname": "change",
                                    "createdAt": "2025-10-12 20:26:19",
                                    "updatedAt": "2025-10-12 20:27:31"
                                },
                                "timestamp": "2025-10-12 20:27:31"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "닉네임 길이 제한", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "닉네임은 10글자 이내 입니다.",
                                "path": "/user/3/nickname"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "401", description = "로그인 인증이 필요합니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 인증이 필요합니다.", value = """
                            {
                                 "code": 401,
                                 "status": "UNAUTHORIZED",
                                 "message": "로그인 인증이 필요합니다.",
                                 "path": "/user/3"
                             }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "회원을 찾을 수 없습니다.",
                                "path": "/user/3/nickname"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 리소스입니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "이미 등록된 닉네임", value = """
                            {
                                 "code": 409,
                                 "status": "CONFLICT",
                                 "message": "이미 등록된 닉네임 입니다.",
                                 "path": "/user/4/nickname"
                             }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> editNickName(@PathVariable("userId")
                                                     @NotNull
                                                     @Positive Long userId,
                                                     @RequestBody
                                                     @Valid NicknameEditRequestDto request);


    @Operation(summary = "회원 삭제", description = "특정 회원 PK를 통해 회원을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 완료"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "삭제 성공", value = """
                            {}
                            """)
            })),
            @ApiResponse(responseCode = "401", description = "로그인 인증이 필요합니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 인증이 필요합니다.", value = """
                            {
                                 "code": 401,
                                 "status": "UNAUTHORIZED",
                                 "message": "로그인 인증이 필요합니다.",
                                 "path": "/user/3"
                             }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "회원을 찾을 수 없습니다.",
                                "path": "/user/3"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> deletePublicUser(@PathVariable("userId")
                                                         @NotNull
                                                         @Positive Long userId);
}
