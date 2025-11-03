package com.example.KTB_7WEEK.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class RegistUserRequestDto {

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @Schema(description = "회원가입시 사용할 이메일", example = "test@test.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 1, max = 20)
    @Schema(description = "회원가입시 사용할 비밀번호", example = "1q2w3e4r!Q")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    @Size(min = 1, max = 10)
    @Schema(description = "회원가입시 사용할 닉네임", example = "nickname")
    private String nickname;

    @Schema(description = "회원가입시 사용할 프로필 이미지 URL", example = "https://www.test.com")
    private String profileImage;

    public RegistUserRequestDto() {
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
