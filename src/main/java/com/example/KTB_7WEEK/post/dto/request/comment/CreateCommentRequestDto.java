package com.example.KTB_7WEEK.post.dto.request.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public class CreateCommentRequestDto {
    @Positive(message = "작성자 PK는 양수입니다.")
    @Schema(description = "User PK", example = "1")
    private long userId;

    @NotBlank(message = "댓글은 공백 저장이 불가합니다.")
    @Size(min = 1, max = 200, message = "댓글은 1자 이상 200자 이내 입니다.")
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;

    public CreateCommentRequestDto() {
    }

    public long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
