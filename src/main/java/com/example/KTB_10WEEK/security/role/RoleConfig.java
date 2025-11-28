package com.example.KTB_10WEEK.security.role;

import com.example.KTB_10WEEK.security.authority.Authority;
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
    private final Authority[] permittedActions;

    RoleConfig(String role, String authority, String[] permittedUrl, Authority[] permittedActions) {
        this.role = role;
        this.authority = authority;
        this.permittedUrl = permittedUrl;
        this.permittedActions = permittedActions;
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

    public Authority[] getPermittedActions() {
        return permittedActions;
    }

    // 행위 권한 리스트, 역할 제외
    public List<String> getPermittedActionsList() {
        return Arrays.stream(permittedActions)
                .map(Authority::getAuthority)
                .collect(Collectors.toList());

    }

    // 모든 권한 리스트
    public List<String> getAuthorityList() {
        List<String> list = Arrays.stream(permittedActions)
                .map(Authority::getAuthority)
                .collect(Collectors.toList());

        list.add(this.authority);
        return list;
    }

    // 모든 권한 리스트 -> new SimpleGrantedAuthority
    public List<SimpleGrantedAuthority> getAllAuthorityList() {
        List<SimpleGrantedAuthority> list = Arrays.stream(permittedActions)
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        list.add(new SimpleGrantedAuthority(this.authority));
        return list;
    }

    public static RoleConfig from(String role) {
        return Arrays.stream(values())
                .filter(roleConfig -> roleConfig.getRole().equals(role))
                .findFirst()
                .orElse(RoleConfig.USER);
    }

}
