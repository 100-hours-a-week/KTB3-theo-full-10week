package com.todayseafood.api.app.exception.handler;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // COMMON
    IMAGE_NOT_FOUND(400, HttpStatus.BAD_REQUEST, "이미지 파일을 찾을 수 없습니다."),
    TOO_LARGE_IMAGE(413, HttpStatus.PAYLOAD_TOO_LARGE, "이미지 용량을 초과하였습니다."),
    SAVE_IMAGE_FAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장 실패"),

    // USER
    INVALID_PASSWORD(400, HttpStatus.BAD_REQUEST, "비밀번호 형식 불일치(8자 이상 20자 이하 / 대문자, 소문자, 숫자, 특수문자 각각 1개씩 포함해야 합니다."),
    INVALID_EMAIL(400, HttpStatus.BAD_REQUEST, "이메일 형식 불일치(영 대소문자, 숫자만 가능합니다) ex) test@test.com"),
    INVALID_NICKNAME_LENGTH(400, HttpStatus.BAD_REQUEST, "닉네임은 10글자 이내 입니다."),
    WHITESPACE_NOT_ALLOWED(400, HttpStatus.BAD_REQUEST, "공백, 띄워쓰기를 없애주세요"),
    INVALID_USER_ID(400, HttpStatus.BAD_REQUEST, "UserId은 1이상 입니다."),
    USER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    EMAIL_ALREADY_REGISTERED(409, HttpStatus.CONFLICT, "이미 등록된 이메일 입니다."),
    NICKNAME_ALREADY_REGISTERED(409, HttpStatus.CONFLICT, "이미 등록된 닉네임 입니다."),
    USER_UPDATE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "유저 업데이트 실패"),
    USER_CREATE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "유저 생성 실패"),
    USER_DELETE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "유저 삭제 실패"),
    NICKNAME_UPDATE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "유저 닉네임 수정 실패"),
    FAIL_USER_EMAIL_MAPPING(500,HttpStatus.INTERNAL_SERVER_ERROR, "유저&이메일 매핑 실패"),
    FAIL_USER_EMAIL_MAPPING_DELETE(500, HttpStatus.INTERNAL_SERVER_ERROR, "유저&이메일 매핑 정보 삭제 실패"),
    SAVE_PROFILE_IMAGE_FAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "프로필 이미지 저장 실패"),
    DELETE_PROFILE_IMAGE_FAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "프로필 이미지 삭제 실패"),


    // POST
    INVALID_POST_ID(400, HttpStatus.BAD_REQUEST, "PostId은 1이상 입니다."),
    ALREADY_LIKED_POST(400, HttpStatus.BAD_REQUEST, "이미 좋아요 누른 게시글입니다."),
    POST_TITLE_LENGTH_OVER(400, HttpStatus.BAD_REQUEST, "게시글 제목 최대 26자 입니다."),
    POST_NOT_FOUND(404, HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    POST_CREATE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "게시글 생성 실패"),
    POST_UPDATE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "게시글 수정 실패"),
    POST_DELETE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "게시글 삭제 실패"),
    INCREASE_HIT_FAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "조회수 증가 실패"),
    INCREASE_LIKE_FAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "좋아요 수 증가 실패"),
    SAVE_ARTICLE_IMAGE_FAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "게시글 이미지 저장 실패"),
    DELETE_ARTICLE_IMAGE_FAIL(500, HttpStatus.INTERNAL_SERVER_ERROR, "게시글 이미지 삭제 실패"),


    // COMMENT
    NO_COMMENT_NOT_ALLOWED(400, HttpStatus.BAD_REQUEST, "댓글은 1자 이상 입니다."),
    INVALID_COMMENT_ID(400, HttpStatus.BAD_REQUEST, "CommentId은 1이상 입니다."),
    COMMENT_NOT_FOUND(404, HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    COMMENT_CREATE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "댓글 생성 실패"),
    COMMENT_UPDATE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "댓글 수정 실패"),
    COMMENT_DELETE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "댓글 삭제 실패"),

    // Auth
    INVALID_TOKEN_SIGNATURE(400, HttpStatus.BAD_REQUEST, "서명값이 올바르지 않은 토큰입니다."),
    ALREADY_ROTATED_TOKEN(400, HttpStatus.BAD_REQUEST, "사용불가능한 토큰입니다."),
    NOT_JWT_TOKEN(400, HttpStatus.BAD_REQUEST, "JWT 토큰이 아닙니다."),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED,"인증 실패"),
    ALREADY_EXPIRED_TOKEN(401, HttpStatus.UNAUTHORIZED,"기간이 만료된 토큰입니다."),
    FORBIDDEN(403, HttpStatus.FORBIDDEN,"인가 실패"),
    REFRESH_TOKEN_NOT_FOUND(404, HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."),
    FAIL_TOKEN_EXPIRE(500, HttpStatus.INTERNAL_SERVER_ERROR, "토큰 무효화 실패"),
    FAIL_REFRESH_TOKEN_SAVE(500, HttpStatus.INTERNAL_SERVER_ERROR, "리프레시 토큰 저장 실패");



    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
