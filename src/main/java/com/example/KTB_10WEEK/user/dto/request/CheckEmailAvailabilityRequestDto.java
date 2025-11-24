package com.example.KTB_10WEEK.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class CheckEmailAvailabilityRequestDto {
    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @Schema(description = "중복 검사 대상 이메일", example = "test@test.com")
    private String email;

    public CheckEmailAvailabilityRequestDto() {
    }
}
