package com.example.KTB_10WEEK.app.security.role;

import com.example.KTB_10WEEK.app.security.authority.Authority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RoleConfig {
    USER(UserRole.ROLE, UserRole.AUTHORITY, UserRole.PERMITTED_URL, UserRole.AUTHORITIES),
    ADMIN(AdminRole.ROLE, AdminRole.AUTHORITY, AdminRole.PERMITTED_URL, AdminRole.AUTHORITIES);

    private final String role; // "USER", "ADMIN"
    private final String authority; // "ROLE_USER", "ROLE_ADMIN"
    private final String[] permittedUrl;
    private final Authority[] authorities;

    RoleConfig(String role, String authority, String[] permittedUrl, Authority[] authorities) {
        this.role = role;
        this.authority = authority;
        this.permittedUrl = permittedUrl;
        this.authorities = authorities;
    }

    public String getRole() {
        return role;
    }

    public String getAuthority() {
        return authority;
    }

    public String[] getPermittedUrl() {
        return permittedUrl;
    }

    public Authority[] getAuthorities() {
        return authorities;
    }

    public List<String> getAuthorityList() {
        return Arrays.stream(authorities)
                .map(Authority::getAuthority)
                .collect(Collectors.toList());
    }

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorityList() {
        return Arrays.stream(authorities)
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

    public static RoleConfig from(String role) {
        return Arrays.stream(values())
                .filter(roleConfig -> roleConfig.getRole().equals(role))
                .findFirst()
                .orElse(RoleConfig.USER);
    }

}
