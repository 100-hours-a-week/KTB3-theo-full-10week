package com.example.KTB_10WEEK.app.security.role;

import com.example.KTB_10WEEK.app.security.authority.Authority;
import java.util.List;

public final class UserRole {
    private UserRole() {
    }

    public final static String ROLE = "USER";
    public final static String AUTHORITY = "ROLE_USER";
    public final static String[] PERMITTED_URL = new String[]{};
    public final static Authority[] AUTHORITIES = new Authority[]{
            Authority.CREATE_POST,
            Authority.UPDATE_POST,
            Authority.DELETE_POST,

            Authority.CREATE_COMMENT,
            Authority.UPDATE_COMMENT,
            Authority.DELETE_COMMENT,
    };
}
