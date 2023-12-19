package com.example.code.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_ADMIN,
    ROLE_MODERATOR,
    ROLE_READER,
    ADMIN,
    MODERATOR,
    READER
}
