package com.example.KTB_10WEEK.app.security.role;

import com.example.KTB_10WEEK.app.security.authority.Authority;

public final class AdminRole {
    private AdminRole() {};

    public final static String ROLE = "ADMIN";
    public final static String AUTHORITY = "ROLE_ADMIN";
    public final static String[] PERMITTED_URL = new String[]{
            "/admin/**"
    };
    public final static Authority[] AUTHORITIES = new Authority[]{
            Authority.CREATE_POST,
            Authority.UPDATE_POST,
            Authority.DELETE_POST,

            Authority.CREATE_COMMENT,
            Authority.UPDATE_COMMENT,
            Authority.DELETE_COMMENT,
    };

}
