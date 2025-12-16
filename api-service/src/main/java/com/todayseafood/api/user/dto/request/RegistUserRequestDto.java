package com.todayseafood.api.user.dto.request;

import com.todayseafood.api.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
@Getter
@Setter
public class RegistUserRequestDto {

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @Schema(description = "회원가입시 사용할 이메일", example = "test@test.com")
    private String email;

    @Schema(description = "회원 권한(일반 회원, 관리자)", example = "USER, ADMIN")
    private Role role;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{8,20}$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다.")
    @Schema(description = "회원가입시 사용할 비밀번호", example = "1q2w3e4r!Q")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    @Size(min = 1, max = 10)
    @Schema(description = "회원가입시 사용할 닉네임", example = "nickname")
    private String nickname;

    private MultipartFile profileImage;

    public RegistUserRequestDto() {
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
