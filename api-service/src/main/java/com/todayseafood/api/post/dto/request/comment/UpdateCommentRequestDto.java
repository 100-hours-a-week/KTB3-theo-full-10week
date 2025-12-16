package com.todayseafood.api.post.dto.request.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public class UpdateCommentRequestDto {
    @NotBlank(message = "댓글은 필수 입력 입니다.")
    @Size(min = 1, max = 200, message = "댓글은 1자 이상 200자 이내입니다.")
    @Schema(description = "댓글 수정본", example = "수정한 댓글 내용")
    private String content;

    public UpdateCommentRequestDto() {
    }

    public String getContent() {
        return content;
    }
}
