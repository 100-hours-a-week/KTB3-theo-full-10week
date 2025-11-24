package com.example.KTB_10WEEK.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class NicknameEditRequestDto {
    @NotBlank(message = "닉네임은 필수 입력입니다.")
    @Size(min = 1, max = 10, message = "닉네임은 1자 이상 10자 이내입니다.")
    @Schema(description = "변경할 닉네임", example = "Changed Nickname")
    private String nickname;

    public NicknameEditRequestDto() {

    }
}
