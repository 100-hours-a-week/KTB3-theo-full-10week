package com.example.KTB_10WEEK.app.response;

import com.example.KTB_10WEEK.app.util.DateTimePattern;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseResponse<T> {
    @Schema(description = "응답 결과 메세지", example = "응답 결과 메세지")
    private String message;
    @Schema(description = "응답 바디 데이터", example = "T data <- 응답 데이터")
    private T data;
    @Schema(description = "API 처리 완료 시간", example = "2025-10-10 20:00:00")
    private String timestamp;

    public BaseResponse() {
    }

    public BaseResponse(ResponseMessage message, T data) {
        this.message = message.getMessage();
        this.data = data;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter
                .ofPattern(DateTimePattern.DEFAULT_DATE_TIME));
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return this.data;
    }

    public String getTimestamp() {
        return this.timestamp;
    }
}
