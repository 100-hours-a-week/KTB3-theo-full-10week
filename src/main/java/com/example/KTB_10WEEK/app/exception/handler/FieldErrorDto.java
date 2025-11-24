package com.example.KTB_10WEEK.app.exception.handler;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class FieldErrorDto {
    private int code;
    private HttpStatus status;
    private ArrayList<String> errMsg;
    private String path;

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
