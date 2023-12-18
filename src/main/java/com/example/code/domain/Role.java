package com.example.code.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    READER("READER");

    private final String authority;


    @Override
    public String getAuthority() {
        return authority;
    }
}
