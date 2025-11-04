package com.example.KTB_7WEEK.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class LikePostRequestDto {

    @Positive(message = "회원 PK는 양수입니다.")
    @Schema(description = "게시글 좋아요 선택한 회원 PK", example = "1")
    private long userId;

    public LikePostRequestDto() {
    }

    public LikePostRequestDto(long userId) {
        this.userId = userId;
    }
}
