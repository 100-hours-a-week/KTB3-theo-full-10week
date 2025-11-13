package com.example.KTB_7WEEK.app.response;

public enum ResponseMessage {
    // USER
    LOGIN_SUCCESS("Login Success"),
    LOGIN_FAIL("Login Fail"),
    LOGOUT_SUCCESS("Logout Success"),
    USER_REGISTER_SUCCESS("User Register Success"),
    USERINFO_LOAD_SUCCESS("UserInfo Load Success"),
    USER_DELETE_SUCCESS("User Delete Success"),
    NICKNAME_IS_AVAILABLE("Nickname is Available"),
    NICKNAME_IS_NOT_AVAILABLE("Nickname is Not Available"),
    EMAIL_IS_NOT_AVAILABLE("Email is Not Available"),
    EMAIL_IS_AVAILABLE("Email is Available"),
    NICKNAME_EDIT_SUCCESS("Nickname Edit Success"),
    PASSWORD_CHANGE_SUCCESS("Password Change Success"),
    EDIT_PROFILE_SUCCESS("Profile Edit Success"),

    // POST
    POST_REGISTER_SUCCESS("Post Register Success"),
    POSTS_LOAD_SUCCESS("Posts Load Success"),
    POST_INFO_LOAD_SUCCESS("Post Info Load Success"),
    MY_POST_UPDATE_SUCCESS("My Post Update Success"),
    POST_DELETE_SUCCESS("Post Delete Success"),
    INCREASE_HIT_SUCCESS("Increase Hit Success"),
    LIKE_POST_SUCCESS("Like Post Success"),
    CANCEL_LIKE_POST_SUCCESS("Cancel Like Post Success"),

    // COMMENT
    COMMENT_CREATE_SUCCESS("Comment Create Success"),
    COMMENT_LOAD_SUCCESS("Comment Load Success"),
    COMMENTS_LOAD_SUCCESS("Comments Load Success"),
    COMMENT_UPDATE_SUCCESS("Comment Update Success"),
    COMMENT_DELETE_SUCCESS("Comment Delete Success");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
