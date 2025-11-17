package com.example.KTB_7WEEK.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
@Getter
@Setter
public class EditProfileRequestDto {

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    @Size(min = 1, max = 10)
    @Schema(description = "회원가입시 사용할 닉네임", example = "nickname")
    private String nickname;

    private String oldFileName;

    private MultipartFile profileImage;

    public EditProfileRequestDto() {};
}
