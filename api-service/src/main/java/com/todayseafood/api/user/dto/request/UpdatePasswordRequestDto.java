package com.todayseafood.api.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;


@Validated
@Getter
public class UpdatePasswordRequestDto {
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 1, max = 20)
    @Schema(description = "변경 요청 비밀번호", example = "1q2w3e4r!Q!Q")
    private String password;

    public UpdatePasswordRequestDto() {}
}
