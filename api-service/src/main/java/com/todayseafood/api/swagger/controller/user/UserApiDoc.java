package com.todayseafood.api.swagger.controller.user;

import com.todayseafood.api.app.response.BaseResponse;
import com.todayseafood.api.user.dto.request.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "회원 API")
public interface UserApiDoc {
    @Operation(summary = "회원정보 조회", description = "회원 PK를 통해 특정 회원을 조회합니다.")
    public ResponseEntity<BaseResponse> findByPublicUserId(@PathVariable
                                                           @NotNull
                                                           @Positive Long userId);

    @Operation(summary = "회원가입", description = "이메일/비밀번호/닉네임/프로필이미지를 입력받아 새로운 회원을 생성합니다.")
    public ResponseEntity<BaseResponse> createPublicUser(@RequestBody @Valid RegistUserRequestDto request);

    @Operation(summary = "닉네임 중복 확인", description = "사용중인 닉네임인지 확인합니다.")
    public ResponseEntity<BaseResponse> doubleCheckNickname(@RequestBody
                                                            @Valid CheckNicknameAvailabilityRequestDto request);

    @Operation(summary = "이메일 중복 확인", description = "사용중인 이메일인지 확인합니다.")
    public ResponseEntity<BaseResponse> doubleCheckEmail(@RequestBody
                                                         @Valid CheckEmailAvailabilityRequestDto request);

    @Operation(summary = "회원 프로필 수정", description = "특정 회원의 PK를 통해 프로필 이미지, 닉네임을 수정합니다.")
    public ResponseEntity<BaseResponse> editProfile(@PathVariable("userId")
                                                    @NotNull
                                                    @Positive Long userId,
                                                    @Valid
                                                    @ModelAttribute EditProfileRequestDto request);

    @Operation(summary = "비밀번호 수정", description = "특정 회원의 PK를 통해 비밀번호를 수정합니다.")
    public ResponseEntity<BaseResponse> changePassword(@PathVariable("userId")
                                                       @NotNull
                                                       @Positive Long userId,
                                                       @RequestBody
                                                       @Valid UpdatePasswordRequestDto request);


    @Operation(summary = "닉네임 수정", description = "특정 회원의 PK 닉네임을 수정합니다.")
    public ResponseEntity<BaseResponse> editNickName(@PathVariable("userId")
                                                     @NotNull
                                                     @Positive Long userId,
                                                     @RequestBody
                                                     @Valid UpdateNicknameRequestDto request);


    @Operation(summary = "회원 삭제", description = "특정 회원 PK를 통해 회원을 삭제합니다.")
    public ResponseEntity<BaseResponse> deleteUserById(@PathVariable("userId")
                                                         @NotNull
                                                         @Positive Long userId);
}
