package com.todayseafood.api.app.exception.handler;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class FieldErrorDto {
    private final int code;
    private final HttpStatus status;
    private final ArrayList<String> errMsg;
    private final String path;

    public FieldErrorDto(int code, HttpStatus status, ArrayList<String> errMsg, String path) {
        this.code = code;
        this.status = status;
        this.errMsg = errMsg;
        this.path = path;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ArrayList<String> getErrMsg() {
        return errMsg;
    }

    public String getPath() {
        return path;
    }
}
