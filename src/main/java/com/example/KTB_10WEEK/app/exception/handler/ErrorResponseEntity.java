package com.example.KTB_10WEEK.app.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseEntity {
    private int code;
    private HttpStatus status;
    private String message;
    private String path;

    public ErrorResponseEntity(int code, HttpStatus status, String message, String path) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;

    }

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e, String path) {
        ErrorResponseEntity errorResponse = new ErrorResponseEntity(e.getCode(), e.getStatus(), e.getMessage(), path);
        return ResponseEntity.status(e.getStatus())
                .body(errorResponse);
    }

    public static ResponseEntity<FieldErrorDto> toResponseEntity(HttpStatus badRequest, List<FieldError> fieldErrorList, String path) {
        ArrayList<String> errMsg = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for (FieldError err : fieldErrorList) {
            errMsg.add(sb
                    .append(err.getField())
                    .append(" : ")
                    .append(err.getDefaultMessage())
                    .append("(")
                    .append(err.getRejectedValue())
                    .append(")")
                    .toString());
            sb.setLength(0);
        }

        FieldErrorDto errorResponse = new FieldErrorDto(badRequest.value(), badRequest, errMsg, path);
        return ResponseEntity.status(badRequest).body(errorResponse);
    }

    public int getCode() {
        return this.code;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getPath() {
        return path;
    }
}
