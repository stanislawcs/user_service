package com.example.code.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    SHOW_ALL_USERS("SHOW_ALL_USERS"),
    SHOW_ONE_USER("SHOW_ONE_USER"),
    CREATE_USER("CREATE_USER"),
    DELETE_USER("DELETE_USER");

    @Getter
    private final String permission;



}
